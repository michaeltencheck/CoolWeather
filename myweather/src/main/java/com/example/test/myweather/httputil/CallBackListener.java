package com.example.test.myweather.httputil;

/**
 * Created by test on 8/18/2015.
 */
public interface CallBackListener {
    public void onFinish(String response);
    public void onError(Exception e);
}
