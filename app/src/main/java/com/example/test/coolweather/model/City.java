package com.example.test.coolweather.model;

/**
 * Created by test on 8/11/2015.
 */
public class City {
    private int id;
    private String city_name;
    private String city_code;
    private int province_id;

    public int getId() {
        return id;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getCity_code() {
        return city_code;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }
}
