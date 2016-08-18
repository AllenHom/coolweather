package com.starnet.root.coolweather;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by root on 16-8-17.
 */
public class CoolApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        Stetho.initializeWithDefaults(this);
    }

    public static Context getContext(){
        return mContext;
    }
}
