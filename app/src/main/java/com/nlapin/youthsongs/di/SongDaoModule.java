package com.nlapin.youthsongs.di;

import com.nlapin.youthsongs.data.SongDao;

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
    SongDao provideSongDao() {
        return DaggerAppDiComponent.builder().build().getAppDatabase().songDao();
    }
}
