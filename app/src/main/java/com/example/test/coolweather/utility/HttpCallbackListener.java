package com.example.test.coolweather.utility;

/**
 * Created by test on 8/12/2015.
 */
public interface HttpCallbackListener {
    void onFinish(String s);

    void onError(Exception e);
}
