package com.example.lalpari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class review_booking extends AppCompatActivity {
    String Source, Destination, Bus_Type, Bus_Time, Date;
    TextView source, destination, busType, busTime, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_booking);
        source = findViewById(R.id.Source);
        destination = findViewById(R.id.Destination);
        busType = findViewById(R.id.Bus_Type);
        busTime = findViewById(R.id.Bus_Time);
        date = findViewById(R.id.Date);

        Intent intent = getIntent();
        if (intent.hasExtra("Source")) {
            Source = intent.getStringExtra("Source");
            source.setText(Source);
        }
        if (intent.hasExtra("Destination")) {
            Destination = intent.getStringExtra("Destination");
            destination.setText(Destination);
        }
        if (intent.hasExtra("Date")) {
            Date = intent.getStringExtra("Date");
            date.setText(Date);
        }
        if (intent.hasExtra("Bus_Type")) {
            Bus_Type = intent.getStringExtra("Bus_Type");
            busType.setText(Bus_Type);
        }
        if (intent.hasExtra("Bus_Time")) {
            Bus_Time = intent.getStringExtra("Bus_Time");
            busTime.setText(Bus_Time);
        }


    }
}