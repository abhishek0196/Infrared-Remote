package com.example.anurag.myapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import java.io.UnsupportedEncodingException;

public class remote extends AppCompatActivity {
    MqttAndroidClient client;
    DatabaseHelper db;
    String arr[] = new String[10];
    String topic;
    DatabaseHelper dh;
    Cursor data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
        getSupportActionBar().setTitle("Remote");
        Intent intent = getIntent();
        dh = new DatabaseHelper(this);
        final String brand = intent.getStringExtra("brand");
        final String product = intent.getStringExtra("product");
        db = new DatabaseHelper(this);
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(remote.this, "tcp://m11.cloudmqtt.com:11093",clientId);
         data = dh.getData("select * from IRDevices where brand = '"+brand+"' AND product = '"+product+"'");
        data.moveToNext();
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
            options.setUserName("dpuhuqmn");
            options.setPassword("ZDvQNexeeZx9".toCharArray());
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        }
        catch (MqttException e)
        {

        }

        ImageButton b1 = (ImageButton)findViewById(R.id.btn_power);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topic = "MyApp/Mode";
                String payload = brand + "-" + data.getString(2);
                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    message.setRetained(true);
                    client.publish(topic, message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton b2 = (ImageButton)findViewById(R.id.btn_mute);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topic = "MyApp/Mode";
                String payload = brand + "-" + data.getString(3);
                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    message.setRetained(true);
                    client.publish(topic, message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton b3 = (ImageButton)findViewById(R.id.btn_volup);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topic = "MyApp/Mode";
                String payload = brand + "-" + data.getString(4);
                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    message.setRetained(true);
                    client.publish(topic, message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton b4 = (ImageButton)findViewById(R.id.btn_voldn);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topic = "MyApp/Mode";
                String payload = brand + "-" + data.getString(5);
                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    message.setRetained(true);
                    client.publish(topic, message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton b5 = (ImageButton)findViewById(R.id.btn_chup);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topic = "MyApp/Mode";
                String payload = brand + "-" + data.getString(6);
                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    message.setRetained(true);
                    client.publish(topic, message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton b6 = (ImageButton)findViewById(R.id.btn_chdown);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topic = "MyApp/Mode";
                String payload = brand + "-" + data.getString(7);
                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    message.setRetained(true);
                    client.publish(topic, message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });

        Button bx = (Button)findViewById(R.id.btn_done);
        bx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent( remote.this , MainActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
