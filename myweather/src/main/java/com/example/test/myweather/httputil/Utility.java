package com.example.test.myweather.httputil;

import android.text.TextUtils;

import com.example.test.myweather.database.City;
import com.example.test.myweather.database.County;
import com.example.test.myweather.database.MyWeatherDB;
import com.example.test.myweather.database.Province;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by test on 8/18/2015.
 */
public class Utility {
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
