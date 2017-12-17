

#include <ESP8266WiFi.h>
#include <PubSubClient.h>   // Read the rest of the article

#include "DHT.h"
#define DHTPIN 2
#define DHTTYPE DHT11      

DHT meraklimuhendis(DHTPIN, DHTTYPE);  

const char *ssid =  "neco";   
const char *password =  "42judge85";  
 
const char *mqttServer = "m23.cloudmqtt.com";
const int mqttPort = 12620;
const char *mqttUser = "qveftspa";
const char *mqttPassword = "77TCcIMGRk7X";
const char *mqtt_client_name = "arduinoClient1"; // Client connections cant have the same connection name
 
WiFiClient espClient;
PubSubClient client(espClient);
 
void setup() {
 
  Serial.begin(115200 );
  Serial.println("DHT11 test!");

meraklimuhendis.begin();
 
  WiFi.begin(ssid, password);
 
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println("Connecting to WiFi..");
  }
  Serial.println("Connected to the WiFi network");
 
  client.setServer(mqttServer, mqttPort);
  client.setCallback(callback);
 
  while (!client.connected()) {
    Serial.println("Connecting to MQTT...");
 
    if (client.connect("ESP8266Client", mqttUser, mqttPassword )) {
 
      Serial.println("connected");  
 
    } else {
 
      Serial.print("failed with state ");
      Serial.print(client.state());
      delay(2000);
 
    }
  }

 
  
  client.subscribe("Sensors");
 
}
 
void callback(char* topic, byte* payload, unsigned int length) {
 
  Serial.print("Message arrived in topic: ");
  Serial.println(topic);
 
  Serial.print("Message:");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
 
  Serial.println();
  Serial.println("-----------------------");
 
}
 
void loop() {
  
Serial.println("DHT11 test!");

meraklimuhendis.begin();
float h = meraklimuhendis.readHumidity();

float t = meraklimuhendis.readTemperature();

float f = meraklimuhendis.readTemperature(true);


char buf[2];
char result[8];
dtostrf(t,2,0,buf);
strcpy(result,buf);
strcat(result," ");
dtostrf(h,2,0,buf);
strcat(result,buf);
strcat(result," ");
strcat(result,"12");







client.publish("Sensors", result);


Serial.print("Nem: ");
Serial.print(meraklimuhendis.readHumidity());
Serial.println(" %");
Serial.print("Temperature: ");
Serial.print(t);
Serial.println(" *C ");
Serial.print(f);
Serial.println(" *F");

Serial.println(" ");
delay(10000);
  
}

