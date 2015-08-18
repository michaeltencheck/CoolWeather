package com.example.test.myweather.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.myweather.R;
import com.example.test.myweather.database.City;
import com.example.test.myweather.database.County;
import com.example.test.myweather.database.MyWeatherDB;
import com.example.test.myweather.database.Province;

import java.util.ArrayList;
import java.util.List;

public class AreaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    
    private MyWeatherDB myWeatherDB;
    private TextView textView;
    private ListView listView;
    private List<String> dataList = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;
    
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;

    private Province seletedProvince;
    private City seletedCity;
    
    private int currentLevel;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        textView = (TextView) findViewById(R.id.area_title_textView);
        listView = (ListView) findViewById(R.id.choose_area_listView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(arrayAdapter);
        
        listView.setOnItemClickListener(this);
        
        queryProvince();
        
        
    }

    private void queryProvince() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_area, menu);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (currentLevel == LEVEL_PROVINCE) {
            seletedProvince = provinceList.get(position);
            queryCity();
        }else if (currentLevel == LEVEL_CITY) {
            seletedCity = cityList.get(position);
            queryCounty();
        }
    }

    private void queryCounty() {
    }

    private void queryCity() {
    }
}
