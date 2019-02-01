package com.nlapin.youthsongs;

import android.app.Application;

import com.nlapin.youthsongs.di.app.AppDI;
import com.nlapin.youthsongs.di.app.AppDIImpl;

public class YouthSongsApp extends Application {

    AppDI appDI;

    @Override
    public void onCreate() {
        super.onCreate();

        appDI = new AppDIImpl(getApplicationContext());
    }

    public AppDI getAppDI() {
        return appDI;
    }
}
