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

    public synchronized static MyWeatherDB getInstance(Context context) {
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

    public void saveCity(City city) {
        if (city != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("city_name", city.getCity_name());
            contentValues.put("city_code", city.getCity_code());
            contentValues.put("province_id", city.getProvince_id());
            sqLiteDatabase.insert("City", null, contentValues);
        }
    }

    public List<City> loadCity(int province_id) {
        List<City> list = new ArrayList<City>();
        Cursor cursor = sqLiteDatabase.query("City", null, "province_id = ?",
                new String[]{String.valueOf(province_id)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCity_name(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCity_code(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvince_id(province_id);
                list.add(city);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public void saveCounty(County county) {
        if (county != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("county_name", county.getCounty_name());
            contentValues.put("county_code", county.getCounty_code());
            contentValues.put("city_id", county.getCity_id());
            sqLiteDatabase.insert("county", null, contentValues);
        }
    }

    public List<County> loadCounty(int city_id) {
        List<County> list = new ArrayList<County>();
        Cursor cursor = sqLiteDatabase.query("County", null, "city_id = ?",
                new String[]{String.valueOf(city_id)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCounty_name(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCounty_code(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCity_id(city_id);
                list.add(county);
            }while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

}
