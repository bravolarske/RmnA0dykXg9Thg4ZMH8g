 #include <Servo.h>
#include "NewPing.h"
//SERVO
int pinServo = 9;

int pos = 0;    // variable to store the servo position
Servo myservo;  // create servo object to control a servo

long timerOrigin;
//PINNEN

//MOTOR A PINNEN
 int enableA = 12;
 int pinA1 = 5;
 int pinA2 = 6;
 
//Motor B PINNEN
 int enableB = 13;
 int pinB1 = 10;
 int pinB2 = 11;//Define Run variable

 
bool stop = false;
bool autonoom=false;

#define trigPin A0
#define echoPin A1

//LICHT SENSOR PINNEN
int pinl1 = 7;
int pinl2 = 4;
int pinl3 = 3;

//VALUES
//LICHTSENSOR
int lichtsensor1 = 0;
int lichtsensor2 = 0;
int lichtsensor3 = 0;

//MOTOR VALUES

//ULTRA SONAR VALUES
long duration, distance;
NewPing sonar(trigPin,echoPin,1500);

void setup() {
  // put your setup code here, to run once: 
  Serial.begin(9600);


  //Setting pinmodes
  pinMode(pinl1,INPUT);
  pinMode(pinl2,INPUT);
  pinMode(pinl3,INPUT);
  
 pinMode(enableA, OUTPUT);
 pinMode(pinA1, OUTPUT);
 pinMode(pinA2, OUTPUT);

 pinMode(enableB, OUTPUT);
 pinMode(pinB1, OUTPUT);
 pinMode(pinB2, OUTPUT);

  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(9, OUTPUT); 
  
  myservo.attach(pinServo);
}
bool followingline = false;

void loop() {


   //Read Serial Character
   int ibyte =Serial.read();


   //Sending JSON Format
   Serial.println("{\"autonoom\":"+String(autonoom)+",\"distance\":"+String(distance)+",\"followingline\":"+String(followingline)+"}");
   //Enable the motors
   enableMotors();
   lichtsensor1 = digitalRead(7);
   lichtsensor2 = digitalRead(4);
   lichtsensor3 = digitalRead(3);



   //For serial communication if the byte = char stop or start autonomues
   if(ibyte=='m' ||ibyte=='M')
   {
    autonoom=false;
   }
   if(ibyte=='o'||ibyte=='O')
   {
    autonoom=true;
   }

   //SONAR
   distance = sonar.ping_cm();


   //The Autonomus part
   if(autonoom==true)
   {
    //Lijn volgen
    if(lichtsensor1==HIGH&&lichtsensor3==HIGH){
      followingline=true;
       backward(1);
    }
    else if(lichtsensor1==HIGH&&lichtsensor3==LOW){   
      followingline=true;
      turnRight(1);
    }
    else if(lichtsensor1==LOW&&lichtsensor3==HIGH){
      followingline=true;
      turnLeft(1);
    }
    else{
      // if it doesn't find a line it will drive on his sonar
        followingline=false;
        getDistance();
    }
   }
   else{
     
     //MANUAL DRIVING
     if(ibyte=='z'||ibyte=='Z')
     {
      forward(10);
      delay(100);
     }
     else if(ibyte=='s'|| ibyte=='S')
     {
      backward(10);delay(100);    
     }
     else if(ibyte=='q' ||ibyte=='Q'){
       turnLeft(10);
       delay(200);
     }
     else if(ibyte=='d' ||ibyte=='D'){
       turnRight(10);
       delay(200);
     }
     else
     {
      brake(10);
     }
   }
}



int speed = 255;
void motorAOn()
{
 analogWrite(enableA, speed);
}
 
void motorBOn()
{
   digitalWrite(enableB, speed);
}
 
 //disable motors
void motorAOff()
{
 digitalWrite(enableB, LOW);
}
 
void motorBOff()
{
   analogWrite(enableA, LOW);
}

 //motor A controls
void motorAForward()
{
 digitalWrite(pinA1, HIGH);
 digitalWrite(pinA2, LOW);
}
 
void motorABackward()
{
 digitalWrite(pinA1, LOW);
 digitalWrite(pinA2, HIGH);
}
 
//motor B contorls
void motorBForward()
{
 digitalWrite(pinB1, LOW);
 digitalWrite(pinB2, HIGH);
}
 
void motorBBackward()
{
 digitalWrite(pinB1, HIGH);
 digitalWrite(pinB2, LOW);
}
 
//coasting and braking
void motorACoast()
{
 digitalWrite(pinA1, LOW);
 digitalWrite(pinA2, LOW);
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
 
void motorBBrake()
{
 digitalWrite(pinB1, HIGH);
 digitalWrite(pinB2, HIGH);
}
 
//Define High Level Commands
void enableMotors()
{
 motorAOn();
 motorBOn();
}
 
void disableMotors()
{
 motorAOff();
 motorBOff();
}
 
void forward(int time)
{
 motorAForward();
 motorBForward();
 delay(time);
}
 
void backward(int time)
{
 motorABackward();
 motorBBackward();
 delay(time);
}
 
void turnLeft(int time)
{
 motorABackward();
 motorBForward();
 delay(time);
}
 
void turnRight(int time)
{
 motorAForward();
 motorBBackward();
 delay(time);
}
 
void coast(int time)
{
 motorACoast();
 motorBCoast();
 delay(time);
}
 
void brake(int time)
{
 motorABrake();
 motorBBrake();
 delay(time);
}


int Echo(int motorstop){
  int i=myservo.read();
  
  while(i!=motorstop){
      myservo.write(i);
      delay(15);

      if(i<motorstop){
        i++;
      }
      else{
        i--;
      }
  }
  
  digitalWrite(trigPin, LOW);  // Added this line
  delayMicroseconds(2); // Added this line
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10); // Added this line
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);
 
  distance = (duration/2) / 29.1;
}

void getDistance(){ 
  //DISTANCE CHECKER
  if ((distance >= 10) || (distance <= 0)){
    backward(1);
  } 
  else {
    turnLeft(1);
     delay(200);
  }
}
