#include "leds.hpp"

LEDString::LEDString(KinectProcessor *proc) {
    processor = proc;
}

void LEDString::run() {
    SerialPort port("/dev/ttyS0", 9600);
    // SerialPort port("/dev/tty.usbmodem1421", 9600);
    port.openPort();
    
    while (1) {
        double left;
        double right;
        
        processor->t_condition.lock();
        processor->t_condition.wait();
        
        int active = processor->active_contour;
        Rect selected;
        
        if (active != -1) {
            selected = processor->ir_rects[active];
        }
        
        processor->t_condition.unlock();
    
        if (active == -1) {
            left = 0.0;
            right = 0.0;
        } else {
            int center_x_rect = selected.x + (selected.width / 2);
            int center_x_frame = 640 / 2;
            double perc = (center_x_frame - center_x_rect) / (double)(center_x_frame);
            
            left = 1 - abs(perc * perc);
            right = left;
            
            if (perc < 0.0) {
                left += 3 * perc;
            } else if (perc > 0.0) {
                right -= 3 * perc;
            }
        }
        
        char bytes[2];
        bytes[0] = (unsigned char)(MAX(left, 0) * 100);
        bytes[1] = (unsigned char)(MAX(right, 0) * 100);
        port.writeBytes(bytes, 2);
    }
}