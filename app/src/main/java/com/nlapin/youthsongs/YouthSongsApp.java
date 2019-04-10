package com.nlapin.youthsongs;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.nlapin.youthsongs.data.AppDatabase;

import androidx.room.Room;

public class YouthSongsApp extends Application {

    private static final String TAG = "YouthSongsApp";
    private AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        // TODO: 3/22/2019 uncomment on production
        //Fabric.with(this, new Crashlytics());
    }

    public synchronized AppDatabase getAppDatabase() {
        if (appDatabase == null) {
            appDatabase = Room
                    .databaseBuilder(this, AppDatabase.class, "ys-database")
                    .build();
        }
        return appDatabase;
    }
}
