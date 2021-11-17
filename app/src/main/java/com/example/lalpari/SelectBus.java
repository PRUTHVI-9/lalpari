package com.example.lalpari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class SelectBus extends AppCompatActivity {

    Spinner source, destination;
    ImageView logout;
    Button next;
    String[] arr = {"Thane", "Mulund", "Nahur", "Bhandup", "Kanjur", "Vikhroli", "Ghatkopar"};
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bus);
        preferences = getSharedPreferences("MyApp", MODE_PRIVATE);
        editor = preferences.edit();
        source = findViewById(R.id.source);
        destination = findViewById(R.id.destination);
        logout = findViewById(R.id.logout);
        next = findViewById(R.id.next);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SelectBus.this, android.R.layout.simple_list_item_1, arr);

        source.setAdapter(adapter);

        destination.setAdapter(adapter);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectBus.this, MainActivity.class));
                finish();
                editor.putBoolean("is_login",false);
                editor.commit();
            }
        });


        source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("TAG", "onItemSelected: " + source.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        destination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("TAG", "onItemSelected: " + destination.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (source.getSelectedItemPosition() == 0) {
                    Toast.makeText(SelectBus.this, "Please Select Source", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (destination.getSelectedItemPosition() == 0) {
                    Toast.makeText(SelectBus.this, "Please Select Destination", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent NEXT = new Intent(SelectBus.this, ScheduleTiming.class);
                NEXT.putExtra("source", source.getSelectedItem().toString());
                NEXT.putExtra("destination", destination.getSelectedItem().toString());
                startActivity(NEXT);

            }
        });

    }
}