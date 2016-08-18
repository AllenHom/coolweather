package com.starnet.root.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.starnet.root.coolweather.activity.WeatherActivity;
import com.starnet.root.coolweather.model.CommonObjectResponse;
import com.starnet.root.coolweather.model.Weather;
import com.starnet.root.coolweather.net.ApiManager;
import com.starnet.root.coolweather.receiver.AutoUpdateReceiver;
import com.starnet.root.coolweather.util.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 16-8-18.
 */
public class AutoUpdateService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int eightHour = 8*60*60*1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + eightHour;
        Intent i = new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String weatherCode = preferences.getString("weather_code", "");
        ApiManager.getInstance().getTestApi().getWeather(weatherCode).enqueue(new Callback<CommonObjectResponse<Weather>>() {
            @Override
            public void onResponse(Call<CommonObjectResponse<Weather>> call, Response<CommonObjectResponse<Weather>> response) {
                Log.i("TAG", JSON.toJSONString(response.body()));
                Utility.handleWeatherResponse(AutoUpdateService.this, JSON.toJSONString(response.body()), weatherCode);
            }
            @Override
            public void onFailure(Call<CommonObjectResponse<Weather>> call, Throwable t) {
                Log.e("TAG", t.getMessage());
            }
        });
    }
}
