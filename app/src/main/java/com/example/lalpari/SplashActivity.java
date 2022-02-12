package com.example.lalpari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("MyApp", MODE_PRIVATE);
                if (preferences.getBoolean("is_login",false)){
                    startActivity(new Intent(SplashActivity.this,SelectBus.class));
                }else {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }

                finish();
            }
        }, 2000);
    }
}