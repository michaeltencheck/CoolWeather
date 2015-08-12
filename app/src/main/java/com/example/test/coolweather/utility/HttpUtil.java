package com.example.test.coolweather.utility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by test on 8/12/2015.
 */
public class HttpUtil {
    public static void sendHttpRequest
            (final String address, final HttpCallbackListener httpCallbackListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try {
                    URL url = new URL(address);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(8888);
                    httpURLConnection.setReadTimeout(8888);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    if (httpCallbackListener != null) {
                        httpCallbackListener.onFinish(stringBuilder.toString());
                    }
                } catch (Exception e) {
                    if (httpCallbackListener != null) {
                        httpCallbackListener.onError(e);
                    }
                }finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
            }
        }).start();
    }
}
