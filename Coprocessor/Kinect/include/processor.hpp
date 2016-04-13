#ifndef PROCESSOR_HPP_DEF
#define PROCESSOR_HPP_DEF

#include "threading.hpp"
#include <libfreenect.h>
#include <pthread.h>
#include <cv.h>
#include <vector>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>

using namespace std;
using namespace cv;

class KinectProcessor {
    public:
        KinectProcessor();
        void processRGB(Mat mat);
        void processIR(Mat mat);
        
        kinectCondition t_condition;
        vector<vector<Point> > contours;
        vector<vector<Point> > filteredContours;
        vector<vector<Point> > filteredHulls;
        vector<Rect> ir_rects;
        int active_contour;
    private:
        Scalar hsl_low, hsl_high;
        bool show_window;
};

#endif