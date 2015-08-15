package com.example.test.coolweather.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.test.coolweather.db.CoolWeatherDB;
import com.example.test.coolweather.model.City;
import com.example.test.coolweather.model.County;
import com.example.test.coolweather.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by test on 8/12/2015.
 */
public class Utility {
    public static void handleWeatherResponse(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String city_name = weatherInfo.getString("city");
            String weather_code = weatherInfo.getString("cityid");
            String high_temp = weatherInfo.getString("temp1");
            String low_temp = weatherInfo.getString("temp2");
            String weather_des = weatherInfo.getString("weather");
            String public_time = weatherInfo.getString("ptime");
            saveWeatherInfo(context,city_name,weather_code,
                    high_temp,low_temp,weather_des,public_time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void saveWeatherInfo
            (Context context, String city_name, String weather_code,
             String high_temp, String low_temp, String weather_des, String public_time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor =
                PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_seleted", true);
        editor.putString("city_name", city_name);
        editor.putString("weather_code", weather_code);
        editor.putString("high_temp", high_temp);
        editor.putString("low_temp", low_temp);
        editor.putString("weather_des", weather_des);
        editor.putString("public_time", public_time);
        editor.putString("current_date", sdf.format(new Date()));
        editor.commit();
    }

    public synchronized static boolean handleProvinceResponse
            (CoolWeatherDB coolWeatherDB,String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvince_code(array[0]);
                    province.setProvince_name(array[1]);
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCityResponse
            (CoolWeatherDB coolWeatherDB, String response, int province_id) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCitys = response.split(",");
            if (allCitys != null && allCitys.length > 0) {
                for (String c : allCitys) {
                    City city = new City();
                    String[] array = c.split("\\|");
                    city.setCity_code(array[0]);
                    city.setCity_name(array[1]);
                    city.setProvince_id(province_id);
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCountyResponse
            (CoolWeatherDB coolWeatherDB, String response, int city_id) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCountys = response.split(",");
            if (allCountys != null && allCountys.length > 0) {
                for (String co : allCountys) {
                    County county = new County();
                    String[] array = co.split("\\|");
                    county.setCounty_code(array[0]);
                    county.setCounty_name(array[1]);
                    county.setCity_id(city_id);
                    coolWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
}
