const int ogenPin = 8;

void setup() {
  // initialize serial communication:
  Serial.begin(9600);
  
  pinMode(ogenPin, INPUT);
  //analogWrite(ogenPin, HIGH);
}

void loop()
{
  analogWrite(ogenPin, 255);
  delay(100);
}
