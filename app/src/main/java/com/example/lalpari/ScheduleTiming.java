package com.example.lalpari;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleTiming extends AppCompatActivity {
    Button selecttime , selectdate ,booknow;
    String source, destination;
    Spinner Bustype , availablebus;
    String[] bustype ={"Select bus type","AC Bus","Non AC Bus"};
    JSONObject busdata;
    JSONObject Bookings;
    JSONArray Bookinglist;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_timing);
//        selecttime = findViewById(R.id.selecttime);
        Bustype = findViewById(R.id.bustype);
        availablebus = findViewById(R.id.available_bus);
        Bookings = new JSONObject();
        Bookinglist = new JSONArray();

        Intent intent = getIntent();
        if (intent.hasExtra("source")){
            source = intent.getStringExtra("source");
            Log.e("TAG ","onCreate: "+ source );
        }
        if (intent.hasExtra("destination")){
            destination = intent.getStringExtra("destination");
            Log.e("TAG ","onCreate: "+destination );
        }

        selectdate = findViewById(R.id.selectdate);
        booknow = findViewById(R.id.booknow);
        try {
            busdata = new JSONObject("{\"Bookings\":{\"Non_AC_Bus\":{\"8-11-2021\":[\"10:00 am\",\"10:15 am\",\"10:30 am\",\"11:00 am\",\"11:15 am\",\"11:30 am\",\"2:00 pm\",\"7:00 pm\",\"8:00 pm\",\"9:00 pm\"],\"9-11-2021\":[\"10:00 am\",\"10:15 am\",\"10:30 am\",\"11:00 am\",\"11:15 am\",\"11:30 am\",\"2:00 pm\",\"6:00 pm\",\"8:00 pm\",\"9:00 pm\"],\"10-11-2021\":[\"10:00 am\",\"10:15 am\",\"10:30 am\",\"11:00 am\",\"11:15 am\",\"11:30 am\",\"2:00 pm\",\"6:00 pm\",\"7:00 pm\",\"9:00 pm\"],\"11-11-2021\":[\"10:00 am\",\"10:15 am\",\"10:30 am\",\"11:00 am\",\"11:15 am\",\"11:30 am\",\"2:00 pm\",\"6:00 pm\",\"7:00 pm\",\"8:00 pm\"]},\"AC_Bus\":{\"8-11-2021\":[\"10:00 am\",\"10:15 am\",\"10:30 am\",\"11:00 am\",\"11:15 am\",\"11:30 am\",\"2:00 pm\",\"7:00 pm\",\"8:00 pm\",\"9:00 pm\"],\"9-11-2021\":[\"10:00 am\",\"10:15 am\",\"10:30 am\",\"11:00 am\",\"11:15 am\",\"11:30 am\",\"2:00 pm\",\"6:00 pm\",\"8:00 pm\",\"9:00 pm\"],\"10-11-2021\":[\"10:00 am\",\"10:15 am\",\"10:30 am\",\"11:00 am\",\"11:15 am\",\"11:30 am\",\"2:00 pm\",\"6:00 pm\",\"7:00 pm\",\"9:00 pm\"],\"11-11-2021\":[\"10:00 am\",\"10:15 am\",\"10:30 am\",\"11:00 am\",\"11:15 am\",\"11:30 am\",\"2:00 pm\",\"6:00 pm\",\"7:00 pm\",\"8:00 pm\"]}}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        selectdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.e("TAG","onClick: " );
                DatePickerDialog datePickerDialog = new DatePickerDialog(ScheduleTiming.this);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                        int temp = month +1;
                        Log.e("TAG", "onDateSet: " +temp);
                        selectdate.setText(dayofMonth+"-"+temp+"-"+year);
                    }
                });
                datePickerDialog.show();
            }
        });

        Bustype.setAdapter(new ArrayAdapter<>(ScheduleTiming.this,R.layout.support_simple_spinner_dropdown_item, bustype));

        Bustype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.e("TAG","onItemSelected: "+ Bustype.getSelectedItemPosition());
                if (Bustype.getSelectedItem().toString().equalsIgnoreCase("Ac bus") || Bustype.getSelectedItem().toString().equalsIgnoreCase("Non AC Bus")){
                    try {
//                        if (busdata.getJSONObject(Bustype.getSelectedItem().toString().replace(" ","_")).has(selectdate.getText().toString().replace(" ","_")));
                        JSONArray timing = busdata.getJSONObject("Bookings").getJSONObject(Bustype.getSelectedItem().toString().replace(" ","_")).getJSONArray(selectdate.getText().toString());
                        Log.e("TAG","onItemSelected: " +timing);
                        List<String> list = new ArrayList<>();
                        list.add("Select bus");
                        for (int i = 0; i<timing.length(); i++) {
                            Log.e("TAG","onItemSelected: " +timing.getString(i));
                            list.add(timing.getString(i));
                        }
                        Log.e("TAG","onItemSelected: " +list);
                        availablebus.setAdapter(new ArrayAdapter<>(ScheduleTiming.this,android.R.layout.simple_list_item_1, list));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleTiming.this,Home.class);
                startActivity(intent);
                finish();
            }
        });



//        bustype.setAdapter(new ArrayAdapter<>(ScheduleTiming.this,R.layout.support_simple_spinner_dropdown_item,bustype));
//        bustype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//            }

//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {

//            }
//        });

/*
        selecttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(ScheduleTiming.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Log.e("TAG","onTimeSet: " + hourOfDay + "-" + minute );
                        selecttime.setText(+hourOfDay+":"+minute);
                    }
                },10,15,false);
                timePickerDialog.show();
            }
        });

*/

        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectdate.getText().toString().isEmpty()){
                    Toast.makeText(ScheduleTiming.this, "Please Select the Date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Bustype.getSelectedItemPosition() == 0){
                    Toast.makeText(ScheduleTiming.this, "Please Select Bus Type as per Choice", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (availablebus.getSelectedItemPosition() == 0){
                    Toast.makeText(ScheduleTiming.this, "Please Select Available Buses for Bookings", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences preferences = getSharedPreferences("MyApp",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                user_id = preferences.getString("User_Id","");
                Log.e("TAG ","Userid" +user_id );

                try {
                    Bookings.put("User_id",user_id);
                    Bookings.put("Source",source);
                    Bookings.put("Destination",destination);
                    Bookings.put("Date",selectdate.getText().toString());
                    Bookings.put("Bus_Type",Bustype.getSelectedItem().toString());
                    Bookings.put("Bus_Time",availablebus.getSelectedItem().toString());


                    Bookinglist = new JSONArray(preferences.getString("Bookings",""));
                    Bookinglist.put(Bookings);

                    editor.putString("Bookings",Bookinglist.toString());
                    Log.e("TAG ","onClick: " +Bookinglist.toString() );

                    editor.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent reviewbooking = new Intent(ScheduleTiming.this,review_booking.class);
                reviewbooking.putExtra("Bustype",Bustype.getSelectedItem().toString());
                reviewbooking.putExtra("selectbus",availablebus.getSelectedItem().toString());
                startActivity(reviewbooking);


            }
        });
    }
}