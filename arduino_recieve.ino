
#include <SoftwareSerial.h>
#include <IRremote.h>

IRsend irsend;
int RECV_PIN = 12;

IRrecv irrecv(RECV_PIN);
void mode1call(void);
decode_results results;
SoftwareSerial mySerial(10, 11); // RX, TX
String s = "";
char arr[100];
int i =0;
void setup() {
  
Serial.begin(9600);
mySerial.begin(9600);
Serial.println("Enabling IRin");
  irrecv.enableIRIn(); // Start the receiver
  Serial.println("Enabled IRin");

}

void loop() {
  
 if (mySerial.available()) {
    arr[i] = mySerial.read();
    i++;
    delay(1);
  }
  else
  {
    arr[i]='\0';
    
    if(strlen(arr)!=0)
    {
      Serial.print("Recieved Something : ");
      Serial.println(arr);
        if( strcmp( arr , "Mode2") == 0 )
        {
          mySerial.flush();
            delay(5000);
            mode1call();
        }
      else
      {
          char *i,*p , *q;
          p = strtok_r(arr,"-",&i);
          Serial.print(p);
          q = strtok_r(NULL,"\0",&i);
          Serial.print(q);

          unsigned long x = strToHex(q);
          if( strcmp(p,"Sony") == 0 )
          irsend.sendSony( x , 32 );
          else if( strcmp(p,"Samsung") == 0)
          irsend.sendSAMSUNG( x , 32 );
          else if( strcmp(p,"LG") == 0)
          irsend.sendLG( x , 32 );
          else if( strcmp(p,"Panasonic") == 0)
          irsend.sendPanasonic( x , 32 );
          else if( strcmp(p,"Others") == 0)
          irsend.sendNEC( x , 32 );
          
      }
  }
  strcpy(arr,"");
   i=0;
 }//when there is nothing to read
    
}//end of program
void mode1call()
{
          Serial.println("Recieved Something more : ");
            Serial.print("Recieving");
            int val = 0;
            
            while( val < 6 )
            {
            
                if (irrecv.decode(&results)) 
                {
                 //
                 mySerial.flush();
                    if( val == 0 )
                    {
                      Serial.print("MyApp/Register/Power-");
                      mySerial.print("MyApp/Register/Power-");
                      //mySerial.print("jjMyApp/Register/Power-");
                    }
                    if( val == 1 )
                    {
                      Serial.print("MyApp/Register/Mute-");
                      mySerial.print("MyApp/Register/Mute-");
                     //mySerial.print("jjMyApp/Register/Mute-");
                    }
                    if( val == 2 )
                    {
                      Serial.print("MyApp/Register/ChUp-");
                      mySerial.print("MyApp/Register/ChUp-");
                      //mySerial.print("jjMyApp/Register/Power-");
                    }
                    if( val == 3 )
                    {
                      Serial.print("MyApp/Register/ChDown-");
                      mySerial.print("MyApp/Register/ChDown-");
                      //mySerial.print("jjMyApp/Register/Power-");
                    }
                    if( val == 4 )
                    {
                      Serial.print("MyApp/Register/VolUp-");
                      mySerial.print("MyApp/Register/VolUp-");
                      //mySerial.print("jjMyApp/Register/Power-");
                    }
                    if( val == 5 )
                    {
                      Serial.print("MyApp/Register/VolDown-");
                     mySerial.print("MyApp/Register/VolDown-");
                     //mySerial.print("jjMyApp/Register/Power-");
                    }
                    Serial.println(results.value, HEX);
                    
                    mySerial.println(results.value, HEX);
                    
                    val++;
                    irrecv.resume(); // Receive the next value
              }
              delay(1000);
            }
          mySerial.flush();
}


unsigned long strToHex( char *arr )
{
    int l = strlen( arr )-1;
    int p = 0,tp;
    unsigned long val = 0,pow;
    int dig;
    char ch;
    while( l >= 0 )
    {
        ch = arr[l] ;
        switch(ch)
        {
            case 'A':
            dig = 10;
            break;
            case 'B':
            dig = 11;
            break;
            case 'C':
            dig = 12;
            break;
            case 'D':
            dig = 13;
            break;
            case 'E':
            dig = 14;
            break;
            case 'F':
            dig = 15;
            break;
            default:
            dig = ch - 48;
        }
        tp = p;
        pow = 1;
        while(tp > 0)
        {
            pow = pow * 16;
            tp--;
        }
        p++;
        val = val + ( pow * dig);
        l--;
    }
    printf("%lu",val);
    return val;
}


