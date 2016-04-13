#include "serial.hpp"

SerialPort::SerialPort(char *pn, int b) {
    portname = pn;
    baud = b;
}

void SerialPort::openPort() {
    fd = open(portname, O_RDWR | O_NONBLOCK);
    
    memset (&tty, 0, sizeof tty);
    tcgetattr (fd, &tty);
    
    cfsetospeed (&tty, baud);
    cfsetispeed (&tty, baud);
    
    tty.c_cflag = (tty.c_cflag & ~CSIZE) | CS8;
    tty.c_iflag &= ~IGNBRK;
    tty.c_lflag = 0;
    tty.c_oflag = 0;
    
    tty.c_cc[VMIN] = 1;
    tty.c_cc[VTIME] = 5;
    
    tty.c_iflag &= ~(IXON | IXOFF | IXANY);
    
    tty.c_cflag |= (CLOCAL | CREAD);
    tty.c_cflag &= ~(PARENB | PARODD);
    tty.c_cflag |= 0;
    tty.c_cflag &= ~CSTOPB;
    tty.c_cflag &= ~CRTSCTS;
    
    tcsetattr (fd, TCSANOW, &tty);
}

void SerialPort::writeBytes(char *bytes, int size) {
    write(fd, bytes, size);
}