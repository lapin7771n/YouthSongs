package com.nlapin.youthsongs.di;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.nlapin.youthsongs.data.SongRepository;
import com.nlapin.youthsongs.data.local.AppDatabase;
import com.nlapin.youthsongs.data.remote.AuthorsSelectionRepository;

import javax.inject.Singleton;

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
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    SongRepository provideSongRepository() {
        return new SongRepository();
    }

    @Provides
    @Singleton
    AuthorsSelectionRepository provideAuthorsSelectionRepository() {
        return new AuthorsSelectionRepository();
    }
}
