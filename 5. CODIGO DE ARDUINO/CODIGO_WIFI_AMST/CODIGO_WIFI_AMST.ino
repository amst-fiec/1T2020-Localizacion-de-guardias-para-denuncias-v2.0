#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

#define FIREBASE_HOST "report-281701.firebaseio.com"
#define FIREBASE_AUTH "RBZ3HocZ3bGaOyRupqLiCnGLl234CHjhz6XSkyHX"

//
#define WIFI_SSID "NETLIFE-Pereira"
#define WIFI_PASSWORD "POLLOFRITO"
int i = 0;
void setup() {
  Serial.begin(9600);
  // put your setup code here, to run once:
  delay(1000);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);                                     //try to connect with wifi
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("Connected to ");
  Serial.println(WIFI_SSID);
  Serial.print("IP Address is : ");
  Serial.println(WiFi.localIP());                                            //print local IP address
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

void loop() {
  if (Serial.available()) {
    String trama = Serial.readString();
    double latitud = getLat(trama);
    double longitud  = getLon(trama);
    String fecha = getDate(trama);

    Firebase.setFloat("/Guardias/ExigiCwPrpMtCmcazMREiT4Wazc2/latitud", latitud);                                  //setup path and send readings
    if (Firebase.failed()) {

      return;
    }
    Firebase.setFloat("/Guardias/ExigiCwPrpMtCmcazMREiT4Wazc2/longitud", longitud);                                  //setup path and send readings
    if (Firebase.failed()) {

      return;
    }
    Firebase.setString("/Guardias/ExigiCwPrpMtCmcazMREiT4Wazc2/date", fecha);                                  //setup path and send readings
    if (Firebase.failed()) {

      return;
    }
  }
}



double getLat(String trama) {
  int i = trama.indexOf("@");
  int f = trama.indexOf("*");

  String latStr = trama.substring(i + 1, f);
  double latitud = latStr.toDouble();

  return latitud;
}

double getLon(String trama) {
  int f = trama.indexOf("*");
  int f2 = trama.indexOf("|");

  String lonStr = trama.substring(f + 1, f2);
  double longitud = lonStr.toDouble();

  return longitud;
}

String getDate(String trama) {
  int f2 = trama.indexOf("|");
  String fecha = trama.substring(f2 + 1);

  return fecha;
}
