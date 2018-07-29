#include "mbed.h"
#include "SPI.h"
#include "MQTTEthernet.h"
#include "MQTTClient.h"

#define ECHO_SERVER_PORT   7
 
Serial pc(USBTX, USBRX);   //Enabling the Serial transmission between WIZ750SR and PC.
Serial serial(D1,D0);      // Enabling Serial transmission betwenn Wiz750SR and W7500. 
char c[100]="";

int mode = 0;
//long int msgcount = 0;
 
void messageArrived(MQTT::MessageData& md)
{
    
    MQTT::Message &message = md.message;
    
    printf("Payload %.*s\r\n", message.payloadlen, (char*)message.payload);
    //strcpy(arr,"");
    
    
    //arr[i]='\0';
    
      //  pc.printf("Hello 1");
        char *cmp = "2";
        if(  strncmp((char *)message.payload, cmp , 1) == 0  )
        {
    //            serial.printf("Mode2");    
            mode = 1;
        }
        else  
        {
            mode = 0;
            serial.printf("%.*s", message.payloadlen, (char*)message.payload);
        }
} 
 
 
int main(void) {
    printf("Wait a second...\r\n");
    char* topic = "MyApp/Mode";                    //if we are subscribing the acknowledgement.
    MQTTEthernet ipstack = MQTTEthernet();
    
    MQTT::Client<MQTTEthernet, Countdown> client = MQTT::Client<MQTTEthernet, Countdown>(ipstack);
    
    char* hostname = "m11.cloudmqtt.com";   //Give the IP Address of the MQTT Broker.
    int port = 11093;                  // Port number of the MQTT broker.  
    
 
    int rc = ipstack.connect(hostname, port);
    if (rc != 0)
    printf("rc from TCP connect is %d\n", rc);
    printf("Topic: %s\r\n",topic);
    
    MQTTPacket_connectData data = MQTTPacket_connectData_initializer;       
    data.MQTTVersion = 3;
    data.clientID.cstring = "parents";
    data.username.cstring = "dpuhuqmn";
    data.password.cstring = "ZDvQNexeeZx9";
    data.cleansession = 0;

    if ((rc = client.connect(data)) == 0)
    printf("rc from MQTT connect is %d\n", rc);
    
   
    if ((rc = client.subscribe(topic, MQTT::QOS2, messageArrived)) != 0)
    printf("rc from MQTT subscribe is %d\r\n", rc);               
    int i = 0;
   
   while(1)  
   {   
        while(1)
        {
            if( mode == 1 )
            {        
                //client.unsubscribe(topic);
                break;
            }
            else
            {
                  client.yield(10000);
                //pc.printf("%d\n",x);       
            }
        }
        int count = 0; 
        int flg = 0;
        while( count < 6 && mode == 1   )
        {   
           if( mode == 1 )
           {
                pc.printf("Mode Switch : \n");
                while (1) // if anything available/ readable in Serial port 
                {
                       if(serial.readable())
                       {
                           char c1=serial.getc();
                           c[i] = c1;
                           i++;
                           if( c1 == '\n')
                           break;
                        }
                } 
                count++;
                pc.printf("The value returned is %s ",c);         
                char ntopic[100],msg[100];
                char* text = strtok(c,"-");
                strcpy(ntopic,text);
                text = strtok(NULL,"\n");
                strcpy(msg,text);
                MQTT::Message message;
                char buf[100];
                sprintf(buf, "%s", msg);
                message.qos = MQTT::QOS0;
                message.retained = false;
                message.dup = false;            
                message.payload = (void*)msg;
                message.payloadlen = strlen(msg);
                rc = client.publish(ntopic, message);    
                i = 0;
                strcpy(c,"");
            }
        }
        mode = 0;
    }
}
