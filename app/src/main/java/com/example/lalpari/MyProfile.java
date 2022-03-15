package com.example.lalpari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfile extends AppCompatActivity {
    TextView Firstname,Middlename,Lastname,Email,Mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Firstname = findViewById(R.id.tv_firstname);
        Middlename = findViewById(R.id.tv_midlename);
        Lastname = findViewById(R.id.tv_lastname);
        Email = findViewById(R.id.tv_email);
        Mobile = findViewById(R.id.tv_mobile);

        SharedPreferences preferences = getSharedPreferences("MyApp",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReferenceFromUrl("https://swift-ride-22040-default-rtdb.firebaseio.com/user/"+preferences.getString("Mobile","NA"));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("TAG", "onDataChange: " + snapshot.toString());
                String fristname = snapshot.child("firstname").getValue().toString();
                Firstname.setText(fristname);
                String middlename = snapshot.child("middlename").getValue().toString();
                Middlename.setText(middlename);
                String lastname = snapshot.child("lastname").getValue().toString();
                Lastname.setText(lastname);
                String email = snapshot.child("email").getValue().toString();
                Email.setText(email);
                String mobile = snapshot.child("mobile_no").getValue().toString();
                Mobile.setText(mobile);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}