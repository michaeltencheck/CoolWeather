package com.example.test.myweather.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.myweather.R;
import com.example.test.myweather.database.City;
import com.example.test.myweather.database.County;
import com.example.test.myweather.database.MyWeatherDB;
import com.example.test.myweather.database.Province;
import com.example.test.myweather.httputil.CallBackListener;
import com.example.test.myweather.httputil.HttpUtil;
import com.example.test.myweather.httputil.Utility;

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

        myWeatherDB = MyWeatherDB.getInstance(this);
        
        queryProvince();
        
        
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

    private void queryProvince() {
        provinceList = myWeatherDB.loadProvince();
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province p : provinceList) {
                String name = p.getProvince_name();
                dataList.add(name);
            }
            arrayAdapter.notifyDataSetChanged();
            textView.setText("中国");
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            queryFromServer(null, "province");
        }
    }



    private void queryCounty() {
        countyList = myWeatherDB.loadCounty(seletedCity.getId());
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCounty_name());
            }
            arrayAdapter.notifyDataSetChanged();
            textView.setText(seletedCity.getCity_name());
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            queryFromServer(seletedCity.getCity_code(), "county");
        }

    }

    private void queryCity() {
        cityList = myWeatherDB.loadCity(seletedProvince.getId());
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCity_name());
            }
            arrayAdapter.notifyDataSetChanged();
            textView.setText(seletedProvince.getProvince_name());
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            queryFromServer(seletedProvince.getProvince_code(), "city");
        }
    }

    private void queryFromServer(final String code, final String type) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        } else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgress();
        HttpUtil.sendRequest(address, new CallBackListener() {
            boolean result = false;
            @Override
            public void onFinish(String response) {
                if ("province".equals(type)) {
                    result = Utility.handleProvince(myWeatherDB, response);
                }else if ("city".equals(type)) {
                    result = Utility.handleCity(myWeatherDB, response, seletedProvince.getId());
                }else if ("county".equals(type)) {
                    result = Utility.handleCounty(myWeatherDB, response, seletedCity.getId());
                }

                if (result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgress();
                            if ("province".equals(type)) {
                                queryProvince();
                            }else if ("city".equals(type)) {
                                queryCity();
                            }else if ("county".equals(type)) {
                                queryCounty();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgress();
                        Toast.makeText(AreaActivity.this, "加载失败", Toast.LENGTH_LONG).show();
                    }
                });
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
    public void onBackPressed() {
        if (currentLevel == LEVEL_COUNTY) {
            queryCity();
        }else if (currentLevel == LEVEL_CITY) {
            queryProvince();
        } else if (currentLevel == LEVEL_PROVINCE){
            finish();
        }
    }
}
