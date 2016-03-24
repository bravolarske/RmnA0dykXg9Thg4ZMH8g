#include <SoftwareSetial.h>

SoftwareSerial mySerial(10, 11);

void setup() {
  mySerial.begin(9600);
}

void loop() { 
  char c = mySerial.read();

  if (c == 's') {
    brake(10000);
  }
}

void brake(int time)
{
 motorABrake();
 motorBBrake();
 delay(time);
}

void motorABrake()
{
 digitalWrite(pinA1, HIGH);
 digitalWrite(pinA2, HIGH);
}
 
void motorBCoast()
{
 digitalWrite(pinB1, LOW);
 digitalWrite(pinB2, LOW);
}

