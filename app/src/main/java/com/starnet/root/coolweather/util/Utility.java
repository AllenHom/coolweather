package com.starnet.root.coolweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.starnet.root.coolweather.model.City;
import com.starnet.root.coolweather.model.Country;
import com.starnet.root.coolweather.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by root on 16-8-16.
 */
public class Utility {


    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String [] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p:allProvinces
                     ) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceName(array[1]);
                    province.setProvinceCode(array[0]);
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, String resopnse, int provinceId) {
        if (!TextUtils.isEmpty(resopnse)) {
            String[] allCities = resopnse.split(",");
            if (allCities != null && allCities.length>0 ) {
                for (String c:allCities) {
                    String [] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCountriesResponse(CoolWeatherDB coolWeatherDB, String resopnse, int cityId) {
        if (!TextUtils.isEmpty(resopnse)) {
            String[] allCountries = resopnse.split(",");
            if (allCountries != null && allCountries.length>0 ) {
                for (String c:allCountries) {
                    String [] array = c.split("\\|");
                    Country country = new Country();
                    country.setCountryCode(array[0]);
                    country.setCountryName(array[1]);
                    country.setCityId(cityId);
                    coolWeatherDB.saveCountry(country);
                }
                return true;
            }
        }
        return false;
    }

    public static void handleWeatherResponse(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            //String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context, cityName, temp1, temp2, weatherDesp, publishTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void saveWeatherInfo(Context context, String cityName, String temp1, String temp2, String weatherDesp, String publicTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        //editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publicTime);
        editor.putString("current_date", simpleDateFormat.format(new Date()));
        editor.commit();
    }
}
