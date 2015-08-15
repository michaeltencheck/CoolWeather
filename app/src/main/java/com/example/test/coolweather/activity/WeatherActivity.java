package com.example.test.coolweather.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.coolweather.R;
import com.example.test.coolweather.utility.HttpCallbackListener;
import com.example.test.coolweather.utility.HttpUtil;
import com.example.test.coolweather.utility.Utility;

public class WeatherActivity extends AppCompatActivity {
    private LinearLayout weather_info_layout;
    private TextView city_name;
    private TextView public_time;
    private TextView current_data;
    private TextView weather_info;
    private TextView temp1;
    private TextView temp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_show_layout);

        weather_info_layout = (LinearLayout) findViewById(R.id.weather_info_layout);
        city_name = (TextView) findViewById(R.id.city_seleted_textView);
        public_time = (TextView) findViewById(R.id.post_time_textView);
        current_data = (TextView) findViewById(R.id.current_date_textView);
        weather_info = (TextView) findViewById(R.id.weather_info_textView);
        temp1 = (TextView) findViewById(R.id.low_tem_textView);
        temp2 = (TextView) findViewById(R.id.high_tem_textView);
        String countyCode = getIntent().getStringExtra("county_code");

        if (!TextUtils.isEmpty(countyCode)) {
            public_time.setText("同步中....");
            weather_info_layout.setVisibility(View.INVISIBLE);
            city_name.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        } else {
            showWeather();
        }
    }

    private void showWeather() {
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
        city_name.setText(spf.getString("city_name", ""));
        public_time.setText("今天" + spf.getString("public_time", "") + "发布");
        current_data.setText(spf.getString("current_date", ""));
        weather_info.setText(spf.getString("weather_des", ""));
        temp1.setText(spf.getString("low_temp", ""));
        temp2.setText(spf.getString("high_temp", ""));
        weather_info_layout.setVisibility(View.VISIBLE);
        city_name.setVisibility(View.VISIBLE);

    }

    private void queryWeatherCode(String countyCode) {
        String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
        queryFromServer(address, "countyCode");
    }

    private void queryFromServer(final String address, final String type) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String s) {
                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(s)) {
                        String[] array = s.split("\\|");
                        if (array != null && array.length == 2) {
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                }else if ("weatherCode".equals(type)) {
                    Utility.handleWeatherResponse(WeatherActivity.this, s);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(final Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        public_time.setText("同步失败");
                    }
                });
            }
        });
    }

    private void queryWeatherInfo(String weatherCode) {
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        queryFromServer(address, "weatherCode");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
