package com.starnet.root.coolweather.net;

import com.facebook.stetho.inspector.network.ResponseBodyData;
import com.starnet.root.coolweather.model.CommonObjectResponse;
import com.starnet.root.coolweather.model.Weather;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by root on 16-8-17.
 */
public interface WeatherApi {
    @GET("data/list3/city{countryCode}.xml")
    Call<String> getWeatherCode(@Path("countryCode") String countryCode);

    @GET("data/cityinfo/{weatherCode}.html")
    Call<CommonObjectResponse<Weather>> getWeather(@Path("weatherCode") String weatherCode);
}
