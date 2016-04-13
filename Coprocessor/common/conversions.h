#ifndef CONVERSIONS_H
#define CONVERSIONS_H

void intToBytes(int n, char *bytes, int start);
int bytesToInt(char *bytes, int start);
float intToFloat(int i);
int floatToInt(float i);
float bytesToFloat(char *buf, int start);
void floatToBytes(float f, char *buf, int start);

#endif