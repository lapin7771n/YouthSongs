package com.nlapin.youthsongs;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.nlapin.youthsongs.di.app.AppDI;
import com.nlapin.youthsongs.di.app.AppDIImpl;

import io.fabric.sdk.android.Fabric;

public class YouthSongsApp extends Application {

    public static final String TAG = "dd";

    AppDI appDI;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        appDI = new AppDIImpl(getApplicationContext());

        copyDatabase();
    }

    public AppDI getAppDI() {
        return appDI;
    }

    private void copyDatabase() {
        appDI.provideDBHelper();
        SharedPreferences preferences = getSharedPreferences(Constants.FIRST_STARTUP, MODE_PRIVATE);
        boolean firstStartup = preferences.getBoolean(Constants.FIRST_STARTUP, true);
        if (firstStartup) {
            appDI.provideDBHelper().createDataBase();
            preferences
                    .edit()
                    .putBoolean(Constants.FIRST_STARTUP, false)
                    .apply();
        }
    }

    interface Constants {
        String FIRST_STARTUP = "firstStartup";
    }
}
