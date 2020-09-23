// Librerías
#include <SoftwareSerial.h>
#include <TinyGPS++.h>

// Definicion de los pines del Wifi
int WifiRX = 2; // Pin 2 del arduino
int WifiTX = 3; // Pin 3 del arduino
static const uint32_t WifiBaud = 9600;
SoftwareSerial WifiSerial(WifiRX, WifiTX);

//Definición del modulo gps
TinyGPSPlus gps;
static const uint32_t GpsBaud = 9600;

// Trama de información que será enviada al Wifi
String trama;


void setup() {
  WifiSerial.begin(WifiBaud);
  Serial.begin(GpsBaud);
}

void loop() {
  while (Serial.available() > 0)
    if (gps.encode(Serial.read()))
      displayInfo();
}

void displayInfo()
{
  if (gps.location.isValid() && (gps.date.isValid() && gps.time.isValid()))
  {
    trama = "@" + String(gps.location.lat(), 6) + "*" +  String(gps.location.lng(), 6) + "|";
    String fecha = String(gps.date.day()) + "/" + String(gps.date.month()) + "/" + String(gps.date.year()) + " ";
    String tiempo = "";
    if (gps.time.hour() < 10) tiempo.concat("0");
    tiempo.concat(gps.time.hour());
    tiempo.concat(":");
    if (gps.time.minute() < 10) tiempo.concat("0");
    tiempo.concat(gps.time.minute());
    tiempo.concat(":");
    if (gps.time.second() < 10) tiempo.concat("0");
    tiempo.concat(gps.time.second());
    trama.concat(fecha);
    trama.concat(tiempo);
    WifiSerial.println(trama);
    delay(3000);
  }
}
