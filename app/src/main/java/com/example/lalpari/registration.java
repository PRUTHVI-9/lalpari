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
    Button submit,show;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;



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
        show = findViewById(R.id.showhide);
        submit = findViewById(R.id.btn_submit);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReferenceFromUrl("https://swift-ride-22040-default-rtdb.firebaseio.com/user");
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
//                try {
//                    userdetails.put("firstname", firstname.getText().toString());
//                    userdetails.put("lastname", lastname.getText().toString());
//                    userdetails.put("middlename", middlename.getText().toString());
//                    userdetails.put("mobile_no", mobile_no.getText().toString());
//                    userdetails.put("password", password.getText().toString());
////                    userdetails.put("dob",dd.getText().toString() + "/" +mm.getText().toString() + "/"+yyyy.getText().toString());
//                    userlist = new JSONObject(preferences.getString("userlist", "{}"));
//
//                    if (userlist.has(mail.getText().toString())) {
//                        Toast.makeText(registration.this, "User Already Exits", Toast.LENGTH_SHORT).show();
//                        return;
//                    } else {
//                        userlist.put(mail.getText().toString(), userdetails);
//                        editor.putString("userlist", userlist.toString());
//                        editor.putBoolean("is_login",true);
//                        editor.putString("Mobile",mobile_no.getText().toString());
//                        editor.commit();
//                        Intent gotoHome = new Intent(registration.this,ScheduleTiming.class);
//                        startActivity(gotoHome);
//
//
//                    }
//
//                    Log.e("TAG", userlist.toString());
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }

                Map<String,Object> objectMap = new HashMap<>();
                objectMap.put("email",mail.getText().toString());
                objectMap.put("firstname",firstname.getText().toString());
                objectMap.put("midlename",middlename.getText().toString());
                objectMap.put("lastname",lastname.getText().toString());
                objectMap.put("mobile_no",mobile_no.getText().toString());
                objectMap.put("password",password.getText().toString());
                Map<String,Object> userdata = new HashMap<>();
                userdata.put(mobile_no.getText().toString(),objectMap);


                reference.updateChildren(userdata);

                Intent intent = new Intent(registration.this,SelectBus.class);
                startActivity(intent);
                finish();



            }
        });

    }


}

