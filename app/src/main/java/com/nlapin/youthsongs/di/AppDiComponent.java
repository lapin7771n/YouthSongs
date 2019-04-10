package com.nlapin.youthsongs.di;

import com.nlapin.youthsongs.data.AppDatabase;
import com.nlapin.youthsongs.data.SongRepository;

import dagger.Component;

@Component(modules = {AppDiModule.class, SongDaoModule.class})
public interface AppDiComponent {
    AppDatabase getAppDatabase();

    SongRepository getSongRepository();
}
