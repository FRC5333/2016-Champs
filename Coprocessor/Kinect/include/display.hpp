#ifndef DISPLAY_HPP_DEF
#define DISPLAY_HPP_DEF

#include "kinect.hpp"
#include <opencv2/highgui/highgui.hpp>

class DisplayKinect {
    public:
        DisplayKinect(KinectProcessor *proc);
        void run(int *counter, int *fc_proc, int *fc_displayed, int *fc_transmitted);
        
    private:
        KinectProcessor *processor;
        Mat videoMat;
};

#endif