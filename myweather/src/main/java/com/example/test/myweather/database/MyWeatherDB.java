package com.example.test.myweather.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 8/16/2015.
 */
public class MyWeatherDB {
    private static final String DA_NAME = "MyWeatherDB";
    private static MyWeatherDB myWeatherDB;
    private static SQLiteDatabase sqLiteDatabase;
    private static int VERSION = 1;

    private MyWeatherDB(Context context) {
        MyWeatherOpenHelper myWeatherOpenHelper = new MyWeatherOpenHelper
                (context, DA_NAME, null, VERSION);
        sqLiteDatabase = myWeatherOpenHelper.getWritableDatabase();
    }

    private synchronized static MyWeatherDB getInstance(Context context) {
        myWeatherDB = new MyWeatherDB(context);
        return myWeatherDB;
    }

    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("province_name", province.getProvince_name());
            contentValues.put("province_code", province.getProvince_code());
            sqLiteDatabase.insert("Province", null, contentValues);
        }
    }

    public List<Province> loadProvince() {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = sqLiteDatabase.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvince_name(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvince_code(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

}
