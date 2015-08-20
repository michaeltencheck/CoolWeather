package com.example.test.myweather.httputil;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.test.myweather.database.City;
import com.example.test.myweather.database.County;
import com.example.test.myweather.database.MyWeatherDB;
import com.example.test.myweather.database.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by test on 8/18/2015.
 */
public class Utility {
    public static void handleWeatherInfo(Context context,String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
                String city = weatherInfo.getString("city");
                String cityId = weatherInfo.getString("cityid");
                String high_temp = weatherInfo.getString("temp1");
                String low_temp = weatherInfo.getString("temp2");
                String weather_des = weatherInfo.getString("weather");
                String publish_time = weatherInfo.getString("ptime");
                saveWeatherInfo(context,city,cityId,high_temp,low_temp,weather_des,publish_time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveWeatherInfo
            (Context context, String city, String cityId,
             String high_temp, String low_temp, String weather_des, String publish_time) {
        SharedPreferences.Editor editor =
                PreferenceManager.getDefaultSharedPreferences(context).edit();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年M月d日 HH:mm:ss", Locale.CHINA);
        editor.putBoolean("seleted_county", true);
        editor.putString("current_date", simpleDateFormat.format(new Date()));
        editor.putString("city", city);
        editor.putString("cityId", cityId);
        editor.putString("high_temp", high_temp);
        editor.putString("low_temp", low_temp);
        editor.putString("weather_des", weather_des);
        editor.putString("publish_time", publish_time);
        editor.commit();
    }

    public synchronized static boolean handleProvince(MyWeatherDB myWeatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvince_name(array[1]);
                    province.setProvince_code(array[0]);
                    myWeatherDB.saveProvince(province);
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public synchronized static boolean handleCity
            (MyWeatherDB myWeatherDB, String response, int province_id) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0) {
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCity_name(array[1]);
                    city.setCity_code(array[0]);
                    city.setProvince_id(province_id);
                    myWeatherDB.saveCity(city);
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public synchronized static boolean handleCounty
            (MyWeatherDB myWeatherDB, String response, int city_id) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length > 0) {
                for (String cou : allCounties) {
                    String[] array = cou.split("\\|");
                    County county = new County();
                    county.setCounty_name(array[1]);
                    county.setCounty_code(array[0]);
                    county.setCity_id(city_id);
                    myWeatherDB.saveCounty(county);
                }
                return true;
            }
            return false;
        }
        return false;
    }
}
