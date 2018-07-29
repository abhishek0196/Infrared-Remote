package com.example.anurag.myapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class available_remotes extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_remotes);
        db = new DatabaseHelper(this);
        Button b1 = (Button)findViewById(R.id.btn_next);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner s1 = (Spinner)findViewById(R.id.sp1);
                Spinner s2 = (Spinner)findViewById(R.id.sp2);

                Cursor c = db.getData("SELECT * from IRDevices where brand = '"+s1.getSelectedItem().toString()+"' and product = '"+s2.getSelectedItem().toString()+"' ");
                if( c.moveToNext() )
                {
                    Toast.makeText(getApplicationContext(), "Device already registered", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent myIntent = new Intent( available_remotes.this , Main2Activity.class);
                    myIntent.putExtra("brand",s1.getSelectedItem().toString() );
                    myIntent.putExtra("product",s2.getSelectedItem().toString() );
                    startActivity(myIntent);
                }
            }
        });
    }
}
