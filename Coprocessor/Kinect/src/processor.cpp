#include <processor.hpp>

using namespace std;
using namespace cv;

KinectProcessor::KinectProcessor() : hsl_low(0, 100, 0), hsl_high(255, 255, 255), t_condition() { }

void KinectProcessor::processRGB(Mat image) {
    // Do something useful
}

void KinectProcessor::processIR(Mat image) {
    contours.clear();
    filteredContours.clear();
    filteredHulls.clear();
    ir_rects.clear();
    
    // flip(image, image, 0);      // Kinect is mounted upsidedown
    
    Mat tmp(image);
    
    // Usually this is used to convert it to HLS, but since it's an IR (monochrome) image,
    // there's no need to waste the CPU Time doing this 
    // cvtColor(tmp, tmp, CV_RGB2HLS);
    inRange(tmp, hsl_low, hsl_high, tmp);
    
    double largestArea = 0.0;
    active_contour = -1;
    
    findContours(tmp, contours, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_TC89_KCOS);
    int i;
    for (i = 0; i < contours.size(); i++) {
        vector<Point> contour = contours[i];
        Rect r = boundingRect(contour);
       
        double area = contourArea(contour);
        if (area > 300.0) {
            vector<Point> hull;
            convexHull(contour, hull);
            double solidity = 100 * area / contourArea(hull);
            
            if (solidity < 60.0) {
                if (area > largestArea) {
                    largestArea = area;
                    active_contour = filteredContours.size();
                }
                filteredContours.push_back(contour);
                filteredHulls.push_back(hull);
                ir_rects.push_back(r);
            }
        }
    }
    
    t_condition.lock();
    t_condition.signal_all();
    t_condition.unlock();
}