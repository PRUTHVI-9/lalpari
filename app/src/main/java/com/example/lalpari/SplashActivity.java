package com.example.lalpari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences preferences;
        preferences = getSharedPreferences("MyApp",MODE_PRIVATE);
        boolean islogin = preferences.getBoolean("is_login",false);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (islogin==true){
                    Intent intent = new Intent(SplashActivity.this,SelectBus.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        },2000);

    }
}