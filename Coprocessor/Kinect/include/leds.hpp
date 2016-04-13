#ifndef LED_HPP_DEF
#define LED_HPP_DEF

#include "kinect.hpp"
#include "serial.hpp"

class LEDString {
    public:
        LEDString(KinectProcessor *proc);
        void run();
        
    private:
        KinectProcessor *processor;
};

#endif