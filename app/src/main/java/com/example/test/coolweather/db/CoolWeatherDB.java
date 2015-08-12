package com.example.test.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.test.coolweather.model.City;
import com.example.test.coolweather.model.County;
import com.example.test.coolweather.model.Province;

import java.util.ArrayList;
import java.util.List;

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

    public List<Province> loadPorvince(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvince_name(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvince_code(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            } while (cursor.moveToNext());
        } else if (cursor != null){
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
            db.insert("City", null, contentValues);
        }
    }

    public List<City> loadCity() {
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", new String[]{}, "province_id", null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCity_name(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCity_code(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvince_id(cursor.getInt(cursor.getColumnIndex("province_id")));
                list.add(city);
            }while (cursor.moveToNext());
        }else if (cursor != null) {
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
            db.insert("County", null, contentValues);
        }
    }

    public List<County> loadCounty() {
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County", new String[]{}, "city_id", null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCounty_name(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCounty_code(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCity_id(cursor.getInt(cursor.getColumnIndex("city_id")));
                list.add(county);
            }while (cursor.moveToNext());
        }else if (cursor != null) {
            cursor.close();
        }
        return list;
    }
}
