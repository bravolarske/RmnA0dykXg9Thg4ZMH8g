

int Echo(int motorstop,int wait){

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
  delay(wait);
  
  int distance = (duration/2) / 29.1;

  return distance;
}
void getDistance(){ 

  disableMotors();
  int mdistance = Echo(30,100);
  int ldistance = Echo(60,100);
  int rdistance = Echo(90,100);
  
  enableMotors();
  
  
  if ((mdistance >= 40 && ldistance >=40 && rdistance >=40) || (mdistance <= 0 && ldistance <=0 && rdistance <=0)){
    backward(1);
    Serial.println("No hit!!! So Forward");
  } 
  else {
    if(rdistance < 10){
      Serial.println("Move Right");
       turnRight(1);
    }
    else if(ldistance <10){      
      Serial.println("Move Left");
       turnLeft(1);
    }
    Serial.print(distance);
    Serial.println(" cm");
  }

  Serial.println("left=" + (String)ldistance +"middle="+ mdistance + "right=" + (String)rdistance);
  delay(200);
}
