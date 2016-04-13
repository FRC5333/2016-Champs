CC=g++
HEADER_LOC=include
CPP_LOC=src
COMMON_LOC=../common

CFLAGS=-I$(HEADER_LOC) -I$(COMMON_LOC) -I/usr/local/include/libfreenect -I/usr/local/include -g -lm -pthread -L/usr/local/lib/ -lfreenect `pkg-config --cflags --libs opencv`

FILES=$(CPP_LOC)/*.cpp $(COMMON_LOC)/*.c $(COMMON_LOC)/*.cpp

build: $(FILES)
	$(CC) -o kinect_engine $(FILES) $(CFLAGS)