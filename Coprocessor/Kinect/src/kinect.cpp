#include <stdio.h>
#include <inttypes.h>
#include <opencv2/imgproc/imgproc.hpp>
#include "kinect.hpp"

kinectMutex::kinectMutex() {
    pthread_mutex_init( &m_mutex, NULL );
}

void kinectMutex::lock() {
    pthread_mutex_lock( &m_mutex );
}

void kinectMutex::unlock() {
    pthread_mutex_unlock( &m_mutex );
}

pthread_mutex_t *kinectMutex::get() {
    return &m_mutex;
}

kinectCondition::kinectCondition() {
    pthread_cond_init( &m_cond, NULL );
    mtx = kinectMutex();
}

void kinectCondition::signal() {
    pthread_cond_signal( &m_cond );
}

void kinectCondition::signal_all() {
    pthread_cond_broadcast( &m_cond );
}

void kinectCondition::wait() {
    pthread_cond_wait( &m_cond, mtx.get() );
}

void kinectCondition::lock() {
    mtx.lock();
}

void kinectCondition::unlock() {
    mtx.unlock();
}

KinectDevice::KinectDevice(KinectProcessor *proc, int *counter) : videoMat1(Size(640, 480), CV_8UC1), videoMat3(Size(640, 480), CV_8UC3), isrgb(false)  {
    processor = proc;
    count = counter;
}

void KinectDevice::start_kinect() {
    if (freenect_init(&f_ctx, NULL) < 0) {
        printf("Freenect Framework Initialization Failed!\n");
    }
    
    freenect_select_subdevices(f_ctx, (freenect_device_flags)(FREENECT_DEVICE_CAMERA));
    int nr_devices = freenect_num_devices(f_ctx);
    
    if (nr_devices < 1) {
        printf("No Kinect Sensors were detected! %i\n", nr_devices);
        freenect_shutdown(f_ctx);
    }
    
    if (freenect_open_device(f_ctx, &f_dev, 0) < 0) {
        printf("Could not open Kinect Device\n");
        freenect_shutdown(f_ctx);
    }
    
    freenect_set_video_callback(f_dev, rgb_callback);
    if (!isrgb) freenect_set_video_mode(f_dev, freenect_find_video_mode(FREENECT_RESOLUTION_MEDIUM, FREENECT_VIDEO_IR_8BIT));
    else        freenect_set_video_mode(f_dev, freenect_find_video_mode(FREENECT_RESOLUTION_MEDIUM, FREENECT_VIDEO_RGB));
    
    freenect_set_user(f_dev, this);
    
    freenect_start_video(f_dev);
    printf("Kinect Video Started!\n");
    while(freenect_process_events(f_ctx) >= 0) { }
}

void KinectDevice::set_ir() {
    isrgb = false;
}

void KinectDevice::set_rgb() {
    isrgb = true;
}

void KinectDevice::VideoCallback(void *video, uint32_t timestamp) {
    uint8_t* video_cast = static_cast<uint8_t*>(video);
    if (isrgb) {
        videoMat3.data = video_cast;
        processor->processRGB(videoMat3);
    } else {
        videoMat1.data = video_cast;
        cvtColor(videoMat1, videoMat3, CV_GRAY2RGB);
        processor->processIR(videoMat3);
    }
    (*count)++;
}