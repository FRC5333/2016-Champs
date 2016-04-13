#ifndef KINECT_HPP_DEF
#define KINECT_HPP_DEF

#include "processor.hpp"
#include <libfreenect.h>
#include <cv.h>

using namespace cv;

class KinectDevice {
    public:
        KinectDevice(KinectProcessor *proc, int *counter);
        void start_kinect();
        void set_ir();
        void set_rgb();
        void VideoCallback(void *video, uint32_t timestamp);
        
    private: 
        freenect_context *f_ctx;
        freenect_device *f_dev;
        Mat videoMat1;
        Mat videoMat3;
        KinectProcessor *processor;
        bool isrgb;
        int *count;
        
        static void rgb_callback(freenect_device *dev, void *video, uint32_t timestamp) {
            KinectDevice *device = static_cast<KinectDevice*>(freenect_get_user(dev));
            device->VideoCallback(video, timestamp);
        }
};

#endif