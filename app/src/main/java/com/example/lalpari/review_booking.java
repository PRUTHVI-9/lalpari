package com.example.lalpari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class review_booking extends AppCompatActivity {
    String Source,Destination,Date,Bus_time,Bus_type;
    TextView destination,date,source,bus_type,bus_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_booking);
        source = findViewById(R.id.source);
        destination = findViewById(R.id.destination);
        date = findViewById(R.id.date);
        bus_time = findViewById(R.id.bus_time);
        bus_type = findViewById(R.id.bus_type);



        Intent intent = getIntent();
        if (intent.hasExtra("source")) {
            Source = intent.getStringExtra("source");
            source.setText(Source);
        }if (intent.hasExtra("destination")) {
            Destination = intent.getStringExtra("destination");
            destination.setText(Destination);
        }if (intent.hasExtra("date")) {
            Date = intent.getStringExtra("date");
            date.setText(Date);
        }if (intent.hasExtra("BusTime")) {
            Bus_time = intent.getStringExtra("BusTime");
            bus_time.setText(Bus_time);
        }if (intent.hasExtra("Bustype")) {
            Bus_type = intent.getStringExtra("Bustype");
            bus_type.setText(Bus_type);
        }

    }
}