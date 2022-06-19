package com.example.lalpari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText mobile;
    EditText password;
    Button log_in;
    Button registration;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    SqliteDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SqliteDB(this);
//        boolean isloggedIn = preferences.getBoolean("is_login", false);
//        if (isloggedIn) {
//            startActivity(new Intent(MainActivity.this, SelectBus.class));
//            finish();
//        }
        db.storeUser("Pankaj","Jadhav");

        Map<String,String >map =  db.getDataforname("Pankaj");

       String id = map.get(SqliteDB.KEY_ID);
       String name = map.get(SqliteDB.KEY_FIRST);
       String last = map.get(SqliteDB.KEY_LAST);
        Log.e("TAG", "onCreate: "+id+"-"+name+"-"+last );
        mobile = findViewById(R.id.et_mobile);
        password = findViewById(R.id.et_password);
        preferences = getSharedPreferences("MyApp", MODE_PRIVATE);
        editor = preferences.edit();
        log_in = findViewById(R.id.btn_login);
        registration = findViewById(R.id.btn_register);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReferenceFromUrl("https://lalpari-default-rtdb.firebaseio.com/user");

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
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.e("TAG", "onDataChange: " + snapshot.toString());
                        boolean isUserExist = snapshot.child(mobile.getText().toString()).exists();
                        if (isUserExist) {
                            String pw = password.getText().toString();
                            String dbpw = snapshot.child(mobile.getText().toString()).child("password").getValue().toString();
                            if (pw.equals(dbpw)) {
                                Intent intent = new Intent(MainActivity.this, SelectBus.class);
                                startActivity(intent);
                                finish();
                                editor.putString("Mobile", mobile.getText().toString());
                                editor.putBoolean("is_login", true);
                                editor.commit();
                            } else {
                                Toast.makeText(MainActivity.this, "password mismatched", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "user not exist please register", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

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