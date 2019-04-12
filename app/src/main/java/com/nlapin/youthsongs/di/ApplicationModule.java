package com.nlapin.youthsongs.di;

import android.app.Application;
import android.content.Context;

import com.nlapin.youthsongs.data.SongRepository;
import com.nlapin.youthsongs.data.local.AppDatabase;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "ys-local-database")
                .build();
    }

    @Provides
    @Singleton
    SongRepository provideSongRepository(){
        return new SongRepository();
    }
}
