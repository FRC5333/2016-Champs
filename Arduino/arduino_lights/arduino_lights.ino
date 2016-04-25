#include <Adafruit_NeoPixel.h>

#define PIN_RING     11
#define PIXELS_RING  32

#define PIN_STRIP    6
#define PIXELS_STRIP 150

#define HALF_RING    PIXELS_RING / 2

Adafruit_NeoPixel rings = Adafruit_NeoPixel(PIXELS_RING, PIN_RING, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel strip = Adafruit_NeoPixel(PIXELS_STRIP, PIN_STRIP, NEO_GRB + NEO_KHZ800);

void setup() {
  rings.begin();
  strip.begin();
  
  Serial.begin(9600);
  rings.setBrightness(255);
  strip.setBrightness(255);
  
  for(int i = 0; i < PIXELS_RING; i++) {
    rings.setPixelColor(i, rings.Color(255,0,0));
    rings.show();
  }
  
  for(int i = 0; i < PIXELS_STRIP; i++) {
    strip.setPixelColor(i, strip.Color(255,0,255));
    strip.show();
  }
}

void loop() {
  if (Serial.available() >= 2) {
    byte left = Serial.read();
    byte right = Serial.read();
    
    double left_ratio = ((double)left) / 100;
    double right_ratio = ((double)right) / 100;
    
    int adjusted_left = (int)round(left_ratio * HALF_RING);
    int adjusted_right = (int)round(right_ratio * HALF_RING);  
    
    for (int i = 0; i < HALF_RING; i++) {
      if (i < adjusted_left) {
        rings.setPixelColor(i, rings.Color(0,255,0));
      } else {
        rings.setPixelColor(i, rings.Color(255,0,255));
      }
    }
    
    for (int i = HALF_RING; i < PIXELS_RING; i++) {
      int j = (PIXELS_RING - 1 - i);
      if (j < adjusted_right) {
        rings.setPixelColor(i, rings.Color(0,255,0));
      } else {
        rings.setPixelColor(i, rings.Color(255,0,255));
      }
    }
    
    rings.show();
  }
}
