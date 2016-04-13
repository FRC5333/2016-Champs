#ifndef TRANSMIT_HPP_DEF
#define TRANSMIT_HPP_DEF

#include "kinect.hpp"
#include "socket.h"
#include "conversions.h"

class Transmitter {
    public:
        Transmitter(KinectProcessor *proc, char *priority_host);
        void run(int *counter);
        
    private:
        void run_transmitter(int *counter, SOCKET socket);
        KinectProcessor *processor;
        char *host;
        vector<Rect> ir_rects;
        char buf[2048];
};

#endif