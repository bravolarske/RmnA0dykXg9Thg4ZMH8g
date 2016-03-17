const int trigPin = A0;
const int echoPin = A1;

void setup() {
  // initialize serial communication:
  Serial.begin(9600);
  
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
}

long HR_dist=0;
long duration;


void getDistance(){ 
 
 /* The following trigPin/echoPin cycle is used to determine the
 distance of the nearest object by bouncing soundwaves off of it. */ 
 digitalWrite(trigPin, LOW); 
 delayMicroseconds(2); 

 digitalWrite(trigPin, HIGH);
 delayMicroseconds(10); 
 
 digitalWrite(trigPin, LOW);
 duration = pulseIn(echoPin, HIGH);

 //Calculate the distance (in cm) based on the speed of sound.
 HR_dist = duration/58.2;
 
}

void loop()
{
  getDistance();
  Serial.println(HR_dist);
}

