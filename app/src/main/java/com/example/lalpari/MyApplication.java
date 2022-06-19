package com.example.lalpari;

import android.app.Application;

public class MyApplication extends Application {
    public static SqliteDB db;
    @Override
    public void onCreate() {
        db = new SqliteDB(this);
        super.onCreate();
    }
}
