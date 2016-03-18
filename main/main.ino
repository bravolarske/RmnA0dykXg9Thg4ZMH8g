
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


 //ULTRA SONAR PINNEN
int trigPin = A0;
int echoPin = A1;

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

void setup() {
  // put your setup code here, to run once: 
  Serial.begin(9600);
  
  pinMode(pinl1,INPUT);
  pinMode(pinl2,INPUT);
  pinMode(pinl3,INPUT);
  
  
 pinMode(enableA, OUTPUT);
 pinMode(pinA1, OUTPUT);
 pinMode(pinA2, OUTPUT);
 
 pinMode(enableB, OUTPUT);
 pinMode(pinB1, OUTPUT);
 pinMode(pinB2, OUTPUT);
}

void loop() {
  
   enableMotors();
   lichtsensor1 = digitalRead(7);
   lichtsensor2 = digitalRead(4);
   lichtsensor3 = digitalRead(3);
   
   if(lichtsensor1==HIGH&&lichtsensor3==HIGH){
       backward(1);
   }
   else if(lichtsensor1==HIGH&&lichtsensor3==LOW){   
      turnRight(1);
   }
   else if(lichtsensor1==LOW&&lichtsensor3==HIGH){
    turnLeft(1);
   }
   else{
      getDistance();
   }
  // put your main code here, to run repeatedly:
}
