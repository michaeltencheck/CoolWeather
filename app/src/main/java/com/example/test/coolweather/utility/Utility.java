package com.example.test.coolweather.utility;

import android.text.TextUtils;

import com.example.test.coolweather.db.CoolWeatherDB;
import com.example.test.coolweather.model.City;
import com.example.test.coolweather.model.County;
import com.example.test.coolweather.model.Province;

/**
 * Created by test on 8/12/2015.
 */
public class Utility {
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
