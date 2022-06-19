package com.example.lalpari;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SqliteDB extends SQLiteOpenHelper {
    private static int VERSION = 1;
    private static String DB_NAME = "USERS";
    public static String TABLE_NAME = "user";
    public static String KEY_ID = "id";
    public static String KEY_FIRST = "first";
    public static String KEY_LAST = "last";

    public SqliteDB(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY ," + KEY_FIRST + " TEXT," + KEY_LAST + " TEXT)";
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    void storeUser(String first, String last) {
        ContentValues data = new ContentValues();
        data.put(KEY_FIRST, first);
        data.put(KEY_LAST, last);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, data);
    }

    Map<String, String> getDataforname(String name) {
        Map<String, String> map = new HashMap<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,
                        KEY_FIRST, KEY_LAST}, KEY_FIRST + "=?",
                new String[]{name}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String id = cursor.getString(0);
            String first = cursor.getString(1);
            String last = cursor.getString(2);
            map.put(KEY_ID, id);
            map.put(KEY_FIRST, first);
            map.put(KEY_LAST, last);
        }
        return map;
    }

}
