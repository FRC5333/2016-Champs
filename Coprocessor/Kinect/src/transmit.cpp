#include "transmit.hpp"
#include "sleep.h"

#define TRANS_PORT 5802
#define TRANS_HOST_1 "roborio-5333-frc.local"
#define TRANS_HOST_2 "roborio-5333-frc.lan"
#define TRANS_HOST_IP "10.53.33.83"

Transmitter::Transmitter(KinectProcessor *proc, char *priority_host) {
    processor = proc;
    host = priority_host;
}

void Transmitter::run_transmitter(int *counter, SOCKET socket) {
    while (1) {
        ir_rects.clear();
        
        processor->t_condition.lock();
        processor->t_condition.wait();
        
        int active = processor->active_contour;
        int id;
        for (id = 0; id < processor->ir_rects.size(); id++) {
            Rect r = processor->ir_rects[id];
            Rect copy(r.x, r.y, r.width, r.height);
            ir_rects.push_back(copy);
        }
        
        processor->t_condition.unlock();
        
        int i = 0;
        intToBytes(0xBA, buf, i); i+=4;
        intToBytes(active, buf, i); i+=4;
        
        for (id = 0; id < ir_rects.size(); id++) {
            Rect r = ir_rects[id];
            intToBytes(0xBB, buf, i); i+=4;
            intToBytes(r.x, buf, i); i+=4;
            intToBytes(r.y, buf, i); i+=4;
            intToBytes(r.width, buf, i); i+=4;
            intToBytes(r.height, buf, i); i+=4;
        }
        
        intToBytes(0xBC, buf, i); i+=4;
        send(socket, buf, i, 0);
        (*counter)++;
    }
}

void Transmitter::run(int *counter) {
    socket_init();
    
    while (1) {
        int rc = -1;
        char HOST_IP[50];
        bzero(HOST_IP, 50);
        
        printf("Trying to connect to address: %s\n", host);
        rc = hostname_to_ip(host, HOST_IP);
        if (rc != 0) {
            printf("%s failed! Trying %s\n", host, TRANS_HOST_1);
            rc = hostname_to_ip(TRANS_HOST_1, HOST_IP);
            if (rc != 0) {
                printf("%s failed! Trying %s\n", host, TRANS_HOST_2);
                rc = hostname_to_ip(TRANS_HOST_2, HOST_IP);
                if (rc != 0) {
                    printf("%s failed! Using IP %s\n", TRANS_HOST_2, TRANS_HOST_IP);
                    memcpy(HOST_IP, TRANS_HOST_IP, 11);
                }
            }
        }
        
        rc = -1;
        
        printf("Connecting to Host %s\n", HOST_IP);
        SOCKET host_socket = socket_create();
        rc = socket_connect(host_socket, HOST_IP, TRANS_PORT);
        if (rc != 0) {
            printf("Failed to connect to host. Retrying...\n");
        } else {
            printf("Connected to Host %s\n", HOST_IP);
            
            run_transmitter(counter, host_socket);
            
            printf("Client Disconnected. Trying again...\n");
        }
        sleep_ms(2000);
        socket_close(host_socket);
    }
}