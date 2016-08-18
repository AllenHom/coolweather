package com.starnet.root.coolweather.model;

/**
 * Created by root on 16-8-17.
 */
public class CommonObjectResponse<T> {
    private T weatherinfo;

    public T getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(T weatherinfo) {
        this.weatherinfo = weatherinfo;
    }
}
