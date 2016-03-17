
long timerOrigin;
//PINNEN

 int enableA = 12;
 int pinA1 = 5;
 int pinA2 = 6;
//Motor B
 int enableB = 13;
 int pinB1 = 10;
 int pinB2 = 11;//Define Run variable
 
int pinl1 = 7;
int pinl2 = 4;
int pinl3 = 3;

//VALUES
int lichtsensor1 = 0;
int lichtsensor2 = 0;
int lichtsensor3 = 0;

int motor1 =0;
int motor2 =0;
int motor3 =0;
int motor4 =0;
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

int prev1,prev2,prev3;
void loop() {
  
   enableMotors();
   lichtsensor1 = digitalRead(7);
   lichtsensor2 = digitalRead(4);
   lichtsensor3 = digitalRead(3);
   
   
   if(prev1 != lichtsensor1 || prev2 !=lichtsensor2 || prev3 != lichtsensor3){
       Serial.println("l1:"+ (String)lichtsensor1+ "-l2:"+ (String)lichtsensor2+"-l3:"+(String)lichtsensor3);
       prev1 = lichtsensor1;
       prev2 = lichtsensor2;
       prev3 = lichtsensor3;
       
   }
   
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
       brake(50);
   }
  // put your main code here, to run repeatedly:
}


void motorAOn()
{
 analogWrite(enableA, 255);
}
 
void motorBOn()
{
   digitalWrite(enableB, 255);
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
