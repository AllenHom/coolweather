package com.starnet.root.coolweather.net;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.starnet.root.coolweather.model.StringConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 16-8-17.
 */
public class ApiManager {
    private static final String BASE_URL = "http://www.weather.com.cn/";
    private static ApiManager instance;

    private WeatherApi mWeatherApi;
    private Retrofit mRetrofit;

    private ApiManager() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public WeatherApi getTestApi() {
        if (mWeatherApi == null) {
            mWeatherApi = mRetrofit.create(WeatherApi.class);
        }
        return mWeatherApi;
    }

    public static ApiManager getInstance() {
        if (instance == null) {
            synchronized (ApiManager.class) {
                if (instance == null) {
                    instance = new ApiManager();
                }
            }
        }
        return instance;
    }
}
