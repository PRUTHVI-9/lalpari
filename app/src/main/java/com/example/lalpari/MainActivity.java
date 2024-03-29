package com.example.lalpari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText mobile;
    EditText password;
    Button log_in;
    Button registration;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("user");

//        boolean isloggedIn = preferences.getBoolean("is_login", false);
//        if (isloggedIn) {
//            startActivity(new Intent(MainActivity.this, SelectBus.class));
//            finish();
//        }

        mobile = findViewById(R.id.et_mobile);
        password = findViewById(R.id.et_password);
        preferences = getSharedPreferences("MyApp", MODE_PRIVATE);
        editor = preferences.edit();
        log_in = findViewById(R.id.btn_login);
        registration = findViewById(R.id.btn_register);
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mobile.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "mobile no should not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "password should not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(mobile.getText().toString()).exists()) {
                            String pass = dataSnapshot.child(mobile.getText().toString()).child("password").getValue().toString();
                            if (password.getText().toString().equals(pass)) {
                                Intent intent = new Intent(MainActivity.this,SelectBus.class);
                                startActivity(intent);
                                finish();
                                editor.putString("mobile",mobile.getText().toString());
                                editor.putBoolean("is_login",true);
                                editor.commit();

                            }
                        }else {
                            Toast.makeText(MainActivity.this, "user not exits register and try again", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                   /* JSONObject userlist = new JSONObject(preferences.getString("userlist","{}"));
                    Log.e("TAG",userlist.toString());
                    if (userlist.has(mobile.getText().toString())){
                        Toast.makeText(MainActivity.this, "user exits in list", Toast.LENGTH_SHORT).show();
                        JSONObject userdetails = new JSONObject();
                        userdetails = userlist.getJSONObject(mobile.getText().toString());
                        String pwd = userdetails.getString("password");
                        String pw = password.getText().toString();
                        if (pw.equals(pwd)){
                            editor.putBoolean("is_login",true);
                            editor.commit();
                            Intent pass = new Intent(MainActivity.this,SelectBus.class);
                            startActivity(pass);
                            finish();
                        }else {
                            Toast.makeText(MainActivity.this, "password is incorrect , try new", Toast.LENGTH_SHORT).show();
                        }
                        Log.e("TAG","+password");
                    }*/

            }
        });
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent new_registration = new Intent(MainActivity.this, registration.class);
                startActivity(new_registration);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setMessage("Do you want to exit?");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                startActivity(new Intent(MainActivity.this,MainActivity.class));
                finish();
            }
        });
        alert.setNeutralButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.create();
        alert.show();
    }
}