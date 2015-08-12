package com.example.test.coolweather.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.coolweather.R;
import com.example.test.coolweather.db.CoolWeatherDB;
import com.example.test.coolweather.model.City;
import com.example.test.coolweather.model.County;
import com.example.test.coolweather.model.Province;

import java.util.ArrayList;
import java.util.List;

public class ChooseAreaActivity extends AppCompatActivity {
    public static final int PROVINCE_LEVEL = 0;
    public static final int CITY_LEVEL = 1;
    public static final int COUNTY_LEVEL = 2;

    private ListView listView;
    private TextView textView;
    private ProgressDialog progressDialog;
    private CoolWeatherDB coolWeatherDB;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<String>();
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private Province seletedProvince;
    private City seleterCity;
    private int currentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_area);

        textView = (TextView) findViewById(R.id.title_text);
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        coolWeatherDB = CoolWeatherDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == PROVINCE_LEVEL) {
                    seletedProvince = provinceList.get(position);
                    queryCitys();
                }else if (currentLevel == CITY_LEVEL) {
                    seleterCity = cityList.get(position);
                    queryCounties();
                }
            }
        });
        queryProvinces();
    }

    private void queryProvinces() {
        
    }

    private void queryCounties() {
    }

    private void queryCitys() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
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
