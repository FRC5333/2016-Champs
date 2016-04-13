#ifndef SERIAL_HPP_DEF
#define SERIAL_HPP_DEF

#include <errno.h>
#include <termios.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>

class SerialPort {
    public:
        SerialPort(char *portname, int baud);
        void openPort();
        void writeBytes(char *bytes, int size);
    private:
        struct termios tty;
        char *portname;
        int baud, fd;
};

#endif