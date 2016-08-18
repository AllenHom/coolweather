package com.starnet.root.coolweather.util;

/**
 * Created by root on 16-8-16.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
