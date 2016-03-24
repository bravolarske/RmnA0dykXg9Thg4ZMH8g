
void getDistance(){ 
  distance = 0;
  duration = 0;
  digitalWrite(trigPin, LOW);  // Added this line
  delayMicroseconds(2); // Added this line
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10); // Added this line
  digitalWrite(trigPin, LOW);
  
  duration = pulseIn(echoPin, HIGH);
  
  distance = (duration/2) / 29.1;

  
  if (distance >= 200 || distance <= 0){
    enableMotors();
    backward(0.1);
    disableMotors();
    Serial.println("No hit!!! So Forward");
  }
  
  else {
    if(distance < 45){
      brake(100);
      turnLeft(0.1);
    }
    Serial.print(distance);
    Serial.println(" cm");
  }
}
