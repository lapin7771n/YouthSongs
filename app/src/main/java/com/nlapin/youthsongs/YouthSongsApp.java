package com.nlapin.youthsongs;

import android.app.Application;
import android.content.SharedPreferences;

import com.nlapin.youthsongs.data.SongsRepository;
import com.nlapin.youthsongs.data.firebase.FirestoreHelper;
import com.nlapin.youthsongs.di.app.AppDI;
import com.nlapin.youthsongs.di.app.AppDIImpl;

public class YouthSongsApp extends Application {

    private static final String FIRST_STARTUP = "firstStartup";

    private AppDI appDI;

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: 3/22/2019 uncomment on production
        //Fabric.with(this, new Crashlytics());
        appDI = new AppDIImpl(getApplicationContext());

        initFirstStartup();
    }

    public AppDI getAppDI() {
        return appDI;
    }

    private void initFirstStartup() {
        SharedPreferences preferences = getSharedPreferences(FIRST_STARTUP, MODE_PRIVATE);
        boolean firstStartup = preferences.getBoolean(FIRST_STARTUP, true);
        if (firstStartup) {
            SongsRepository songsRepository = appDI.provideSongsRepository();
            FirestoreHelper.getInstance().migrateAllSongsFromFirestore(songsRepository);
            preferences
                    .edit()
                    .putBoolean(FIRST_STARTUP, false)
                    .apply();
        }
    }
}
