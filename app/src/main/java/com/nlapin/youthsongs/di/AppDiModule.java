package com.nlapin.youthsongs.di;

import android.content.Context;

import com.nlapin.youthsongs.data.AppDatabase;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class AppDiModule {

    private AppDatabase appDatabase;

    public AppDiModule(Context context) {
        this.appDatabase = Room.databaseBuilder(context, AppDatabase.class, "ys-database")
                .build();
    }

    @Provides
    public AppDatabase getAppDatabase() {
        if (appDatabase == null) {
            throw new NullPointerException("AppDatabase is null!");
        }
        return appDatabase;
    }
}
