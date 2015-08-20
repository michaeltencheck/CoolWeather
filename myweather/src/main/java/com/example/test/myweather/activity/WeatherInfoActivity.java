package com.example.test.myweather.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.myweather.R;
import com.example.test.myweather.httputil.CallBackListener;
import com.example.test.myweather.httputil.HttpUtil;
import com.example.test.myweather.httputil.Utility;

import java.text.DateFormat;
import java.util.Date;

public class WeatherInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView title;
    private Button switch_city;
    private Button refresh;
    private TextView publish_time;
    private TextView current_date;
    private TextView weatherInfo_dec;
    private TextView temp_show;
    private String county_code;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        title = (TextView) findViewById(R.id.title_textView);
        switch_city = (Button) findViewById(R.id.switch_city_button);
        refresh = (Button) findViewById(R.id.refresh_button);
        publish_time = (TextView) findViewById(R.id.publish_time_textView);
        current_date = (TextView) findViewById(R.id.current_data_textView);
        weatherInfo_dec = (TextView) findViewById(R.id.weather_des_textView);
        temp_show = (TextView) findViewById(R.id.temp_show_textView);

        Intent intent = getIntent();
        String county_name = intent.getStringExtra("county_name");
        county_code = intent.getStringExtra("county_code");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString("county_code", county_code).commit();
        title.setText(county_name);
        queryWeatherCode(county_code);

        switch_city.setOnClickListener(this);
        refresh.setOnClickListener(this);
    }

    private void queryWeatherInfo(String weatherCode) {
        String weatherInfo_address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        HttpUtil.sendRequest(weatherInfo_address, new CallBackListener() {
            @Override
            public void onFinish(String response) {
//                showProgress();
                Utility.handleWeatherInfo(WeatherInfoActivity.this, response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        closeProgress();
                        SharedPreferences weatherInfo = PreferenceManager
                                .getDefaultSharedPreferences(WeatherInfoActivity.this);
                        current_date.setText(weatherInfo.getString("current_date", ""));
                        publish_time.setText(weatherInfo.getString("publish_time", "") + " 发布");
                        weatherInfo_dec.setText(weatherInfo.getString("weather_des", ""));
                        temp_show.setText(weatherInfo.getString("low_temp", "")
                                + " ~ " + weatherInfo.getString("high_temp", ""));
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgress();
                        Toast.makeText(WeatherInfoActivity.this, "加载失败", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void queryWeatherCode(String county_code) {
        String address = "http://www.weather.com.cn/data/list3/city" + county_code + ".xml";
        HttpUtil.sendRequest(address, new CallBackListener() {
            @Override
            public void onFinish(String response) {
                if (response != null) {
                    String[] array = response.split("\\|");
                    String weatherCode = array[1];
//                    Log.d("bbbbb", weatherCode);
                    queryWeatherInfo(weatherCode);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void closeProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather_info, menu);
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

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_city_button:
                Intent intent = new Intent(this, AreaActivity.class);
                intent.putExtra("from_switch_city", true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_button:
                queryWeatherCode(county_code);
        }
    }
}
