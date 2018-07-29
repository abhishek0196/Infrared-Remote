package com.example.anurag.myapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class remote_select extends AppCompatActivity {

    DatabaseHelper dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_select);
        getSupportActionBar().setTitle("Select Remote");

        dh = new DatabaseHelper(this);
        Cursor c = dh.getData("select * from IRDevices where brand  = 'pony' ");
        if( c.moveToNext())
        {

        }
        else
        dh.addData("pony" , "tv" , "eee" ,"eee","eee","eee","eee","eee");

        List<String> spinnerArray =  new ArrayList<String>();
        Cursor cursor = dh.getData("select  brand from IRDevices;");
        while (cursor.moveToNext()) {
            spinnerArray.add(cursor.getString(0));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sItems = (Spinner) findViewById(R.id.brand);
        sItems.setAdapter(adapter);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selItem = sItems.getItemAtPosition(i).toString();
                Cursor cursor = dh.getData("select product from IRDevices where brand = '"+selItem+"'");
                //if (cursor.moveToFirst()) {
                List<String> spinnerArray1 =  new ArrayList<String>();

                //List<String> spinnerArray1 =  new ArrayList<String>();
                while (cursor.moveToNext()) {
                    spinnerArray1.add(cursor.getString(0));
                }
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(remote_select.this, android.R.layout.simple_spinner_item, spinnerArray1);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner sItems1 = (Spinner) findViewById(R.id.device);
                sItems1.setAdapter(adapter1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button button4 = (Button) findViewById(R.id.btn_next);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent( remote_select.this , remote.class);
                Spinner s1 = (Spinner)findViewById(R.id.brand);
                Spinner s2 = (Spinner)findViewById(R.id.device);
                myIntent.putExtra("brand",s1.getSelectedItem().toString() );
                myIntent.putExtra("product",s2.getSelectedItem().toString() );
                startActivity(myIntent);
            }
        });
    }
}