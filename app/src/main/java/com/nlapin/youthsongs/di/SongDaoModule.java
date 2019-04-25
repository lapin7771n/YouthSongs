package com.nlapin.youthsongs.di;

import com.nlapin.youthsongs.data.local.AppDatabase;
import com.nlapin.youthsongs.data.local.FavoriteSongDao;
import com.nlapin.youthsongs.data.local.SongDao;

import dagger.Module;
import dagger.Provides;

/**
 * Dependency Injecting module
 */
@Module
public class SongDaoModule {

    /**
     * This method provides SongDao object
     *
     * @return object for working with local SQL songs table
     */
    @Provides
    static SongDao provideSongDao(AppDatabase appDatabase) {
        return appDatabase.songDao();
    }

    @Provides
    static FavoriteSongDao providerSongDao(AppDatabase appDatabase) {
        return appDatabase.favoriteSongDao();
    }
}
