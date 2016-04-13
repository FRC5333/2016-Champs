#include "kinect.hpp"
#include "display.hpp"
#include "leds.hpp"
#include "transmit.hpp"

#include <sys/time.h>
#include <sys/resource.h>

bool SHOW_WINDOW(false);
bool RGB(false);
bool LED(true);

int processed_frames;
int windowed_frames;
int transmitted_frames;

int prFrameRate;
int wiFrameRate;
int trFrameRate;

char *host = "roborio-5333-frc.local";

pthread_t thread_kinect, thread_framerate, thread_led, thread_transmit;

void *kinectfunc(void *args) {
    ((KinectDevice *)args)->start_kinect();
    return NULL;
}

void *ledfunc(void *args) {
    ((LEDString *)args)->run();
    return NULL;
}

void *frameratefunc(void *args) {
    while(1) {
        printf("Framerate (FPS): (proc => %d, window => %d, trans => %d)\n", processed_frames, windowed_frames, transmitted_frames);
        prFrameRate = processed_frames;
        wiFrameRate = windowed_frames;
        trFrameRate = transmitted_frames;
        processed_frames = 0; windowed_frames = 0; transmitted_frames = 0;
        cvWaitKey(1000);
    }
    return NULL;
}

void *transmitfunc(void *args) {
    ((Transmitter *)args)->run(&transmitted_frames);
    return NULL;
}

int main(int argc, char *args[]) {
    int i;
    for (i = 1; i < argc; i++) {
        if (strcmp(args[i], "--window") == 0) {
            SHOW_WINDOW = true;
            printf("--window flag -> Will create viewing window\n");
        } else if (strcmp(args[i], "rgb") == 0) {
            RGB = true;
            printf("rgb command -> Will use RGB instead of IR\n");
        } else if (strcmp(args[i], "--noled") == 0) {
            LED = false;
            printf("--noled flag -> Will not attempt to communicate with LEDs via serial\n");
        } else {
            host = args[i];
        }
    }
    
    KinectProcessor processor;
    
    KinectDevice kinect(&processor, &processed_frames);
    Transmitter transmitter(&processor, host);
    
    if (RGB) kinect.set_rgb();
    pthread_create(&thread_kinect, NULL, &kinectfunc, (void *)&kinect);
    pthread_create(&thread_framerate, NULL, &frameratefunc, NULL);
    pthread_create(&thread_transmit, NULL, &transmitfunc, (void *)&transmitter);
    
    if (LED) {
        LEDString led(&processor);
        pthread_create(&thread_led, NULL, &ledfunc, (void *)&led);
    }
    
    if (SHOW_WINDOW) {
        DisplayKinect display(&processor);
        display.run(&windowed_frames, &prFrameRate, &wiFrameRate, &trFrameRate);
    }
    pthread_join(thread_kinect, NULL);
    return 0;
}