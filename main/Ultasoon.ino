
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
  
  if(distance < 5){
    forward(1);
  }
  if (distance < 10) {
    turnLeft(1);
  }
  else {
    forward(1);
  }
  if (distance >= 200 || distance <= 0){
    forward(1);
  }
  else {
    if(distance < 15){
      turnLeft(1);
    }
    Serial.print(distance);
    Serial.println(" cm");
  }
  delay(500);
}
