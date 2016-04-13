#include "display.hpp"

using namespace cv;

DisplayKinect::DisplayKinect(KinectProcessor *proc) {
    processor = proc;
    namedWindow("Kinect View", CV_WINDOW_AUTOSIZE);
}

void DisplayKinect::run(int *counter, int *fc_proc, int *fc_displayed, int *fc_transmitted) {
    videoMat = Mat(Size(640, 480), CV_8UC3);
    Point frPointProc(3, 15);
    Point frPointWind(3, 35);
    Point frPointTrans(3, 55);
    Scalar frColor(255, 0, 255);
    char str[20];
    while (1) {
        videoMat.setTo(Scalar(0, 0, 0));
        processor->t_condition.lock();
        processor->t_condition.wait();
        
        int active_contour = processor->active_contour;
        
        if (active_contour >= 0) {
            Scalar color ( 255, 0, 0 );
            drawContours(videoMat, processor->filteredContours, active_contour, color, -1);
            Point pnt = Point(processor->ir_rects[active_contour].tl());
            pnt += Point(3, -4);
            putText(videoMat, "High Goal", pnt, FONT_HERSHEY_SIMPLEX, 0.5, color, 2);
        }
        
        int id;
        for (id = 0; id < processor->filteredContours.size(); id++) {
            Scalar color( 255, 0, 255 );
            Scalar color3( 255, 120, 255 );
            Scalar color4( 255, 0, 0 );
            drawContours(videoMat, processor->filteredContours, id, color);
            drawContours(videoMat, processor->filteredHulls, id, color3);
            rectangle(videoMat, processor->ir_rects[id], color4);
        }
        
        processor->t_condition.unlock();
        
        sprintf(str, "FPS (Processed)   : %d", *fc_proc);
        putText(videoMat, str, frPointProc, FONT_HERSHEY_SIMPLEX, 0.5, frColor, 1);
        sprintf(str, "FPS (Window)      : %d", *fc_displayed);
        putText(videoMat, str, frPointWind, FONT_HERSHEY_SIMPLEX, 0.5, frColor, 1);
        sprintf(str, "FPS (Transmit)    : %d", *fc_transmitted);
        putText(videoMat, str, frPointTrans, FONT_HERSHEY_SIMPLEX, 0.5, frColor, 1);
                
        imshow("Kinect View", videoMat);
        cvWaitKey(1);
        (*counter)++;
    }
}