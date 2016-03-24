
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

  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(9, OUTPUT); 
}
bool stop = false;
void loop() {
  
  Serial.println("SET BT PAGEMODE 3 2000 1");
  Serial.println("SET BT NAME ARDUINOBT");
  Serial.println("SET BT ROLE 0 f 7d00");
  Serial.println("SET CONTROL ECHO 0");
  Serial.println("SET BT AUTH * 12345");
  Serial.println("SET CONTROL ESCAPE - 00 1");
  Serial.println("SET CONTROL BAUD 115200,8n1"); 
  
   enableMotors();
   lichtsensor1 = digitalRead(7);
   lichtsensor2 = digitalRead(4);
   lichtsensor3 = digitalRead(3);

   int byte =Serial.read();
    
   if(byte == 's' || stop == true){
    brake(10000);
      Serial.println("Stop");
      stop = true;
   }
   
   if(byte >1){
      Serial.println((String)byte +"byte");
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
    if(stop == false){
      getDistance();
    }
   }
  // put your main code here, to run repeatedly:
}
