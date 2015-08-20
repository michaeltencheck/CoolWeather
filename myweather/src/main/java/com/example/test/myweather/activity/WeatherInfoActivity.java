package com.example.test.myweather.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.myweather.R;

import java.text.DateFormat;
import java.util.Date;

public class WeatherInfoActivity extends AppCompatActivity {
    private TextView title;
    private Button switch_city;
    private Button refresh;
    private TextView publish_time;
    private TextView current_date;
    private TextView weatherInfo_dec;
    private TextView temp_show;
    private Date currentDate;

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
}
