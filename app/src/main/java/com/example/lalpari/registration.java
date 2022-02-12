package com.example.lalpari;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity {
    EditText firstname, middlename, lastname, mail, password, confirm, mobile_no;
    TextView date;
    Button submit;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        preferences = getSharedPreferences("MyApp", MODE_PRIVATE);
        editor = preferences.edit();
        boolean isloggedIn = preferences.getBoolean("is_login",false);
        if (isloggedIn) {
            startActivity(new Intent(registration.this, SelectBus.class));
            finish();
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user");

//        Map<String,Object> objectMap= new HashMap<>();
//        objectMap.put("Name","Pruthvi");
//        objectMap.put("Last","yadav");
//        objectMap.put("dob","6/12/1997");
//        objectMap.put("email","pruthvi@gmail.com");
//        Map<String,Object> pruthvi= new HashMap<>();
//        pruthvi.put("pruthviraj",objectMap);
//
//
//        Map<String,Object> objectMap1  = new HashMap<>();
//        objectMap1.put("Name","shubham");
//        objectMap1.put("Last","teli");
//        objectMap1.put("dob","6/12/1997");
//        objectMap1.put("email","shubham@gmail.com");
//        Map<String,Object> shubham= new HashMap<>();
//        shubham.put("shubham",objectMap1);
//
//        databaseReference.updateChildren(pruthvi);
        firstname = findViewById(R.id.et_first_name);
        middlename = findViewById(R.id.et_middle_name);
        lastname = findViewById(R.id.et_last_name);
        date = findViewById(R.id.tv_date);
//        mm = findViewById(R.id.et_mm);
//        yyyy = findViewById(R.id.et_yyyy);
        mail = findViewById(R.id.et_email);
        mobile_no = findViewById(R.id.et_number);
        password = findViewById(R.id.et_password);
        confirm = findViewById(R.id.et_confirm_password);
        submit = findViewById(R.id.btn_submit);
        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.e("TAG", "onClick: ");
                DatePickerDialog datePickerDialog = new DatePickerDialog(registration.this);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                        int temp = month + 1;
                        Log.e("TAG", "onDateSet: " + temp);
                        date.setText(year + "-" + temp + "-" + dayofMonth);
                        Log.e("TAG", "onDateSet: " + date.getText().toString());
                    }
                });
                datePickerDialog.show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject userdetails = new JSONObject();
                JSONObject userlist = new JSONObject();
                if (firstname.getText().toString().isEmpty()) {
                    Toast.makeText(registration.this, "firstname should not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (middlename.getText().toString().isEmpty()) {
                    Toast.makeText(registration.this, "middlename should not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (lastname.getText().toString().isEmpty()) {
                    Toast.makeText(registration.this, "lastname should not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (date.getText().toString().isEmpty()) {
                    Toast.makeText(registration.this, "dd should not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mobile_no.getText().toString().isEmpty()) {
                    Toast.makeText(registration.this, "mobile no should not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mobile_no.length()<10){
                    Toast.makeText(registration.this, "Mobile Number Should be at least 10 digit.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mail.getText().toString().isEmpty()) {
                    Toast.makeText(registration.this, "email should not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.getText().toString().matches("[A-Za-z0-9]*")){
                    Toast.makeText(registration.this, "Enter At least one Upper Character.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().isEmpty()) {
                    Toast.makeText(registration.this, "password should not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (confirm.getText().toString().isEmpty()) {
                    Toast.makeText(registration.this, "confirm password should not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().equals(confirm.getText().toString())) {
                    Toast.makeText(registration.this, "password matched", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(registration.this, "password missmatched", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    userdetails.put("firstname", firstname.getText().toString());
                    userdetails.put("lastname", lastname.getText().toString());
                    userdetails.put("middlename", middlename.getText().toString());
                    userdetails.put("mobile_no", mobile_no.getText().toString());
                    userdetails.put("password", password.getText().toString());
//                    userdetails.put("dob",dd.getText().toString() + "/" +mm.getText().toString() + "/"+yyyy.getText().toString());
                    userlist = new JSONObject(preferences.getString("userlist", "{}"));

                    Map<String,Object> user  = new HashMap<>();
//                    user.put("firstname",mail.getText().toString().replace("@","").replace(".",""));
                    user.put("firstname", firstname.getText().toString());
                    user.put("lastname", lastname.getText().toString());
                    user.put("middlename", middlename.getText().toString());
                    user.put("mobile_no", mobile_no.getText().toString());
                    user.put("email", mail.getText().toString());
                    user.put("password", password.getText().toString());
                    Map<String,Object> userdata = new HashMap<>();
                    userdata.put(mobile_no.getText().toString(),user);

                    databaseReference.updateChildren(userdata);


                    if (userlist.has(mail.getText().toString())) {
                        Toast.makeText(registration.this, "User Already Exits", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        userlist.put(mail.getText().toString(), userdetails);
                        editor.putString("userlist", userlist.toString());
                        editor.commit();
                        Intent gotoHome = new Intent(registration.this,ScheduleTiming.class);
                        startActivity(gotoHome);
                    }
                    Log.e("TAG", userlist.toString());
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

    }


}