package com.example.test.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.test.coolweather.model.Province;

/**
 * Created by test on 8/12/2015.
 */
public class CoolWeatherDB {
    private static final String DB_NAME = "CWDB";
    private static final int VERSION = 1;
    private static CoolWeatherDB coolWeatherDB;
    private static SQLiteDatabase db;

    private CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper coolWeatherOpenHelper = new CoolWeatherOpenHelper
                (context, DB_NAME, null, VERSION);
        db = coolWeatherOpenHelper.getWritableDatabase();
    }

    private synchronized static CoolWeatherDB getInstance(Context context) {
        if (coolWeatherDB == null) {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("province_name", province.getProvince_name());
            contentValues.put("province_code", province.getProvince_code());
            db.insert("Province", null, contentValues);
        }
    }

    public void loadPorvince(){
        
    }
}
