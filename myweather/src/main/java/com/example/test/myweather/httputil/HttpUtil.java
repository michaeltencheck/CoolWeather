package com.example.test.myweather.httputil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by test on 8/18/2015.
 */
public class HttpUtil {
    public static void sendRequest(final String address, final CallBackListener callBackListener) {
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
                    BufferedReader bufferedReader = new BufferedReader
                            (new InputStreamReader(inputStream));

                    StringBuilder response = new StringBuilder();
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }

                    if (callBackListener != null) {
                        callBackListener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (callBackListener != null) {
                        callBackListener.onError(e);
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
