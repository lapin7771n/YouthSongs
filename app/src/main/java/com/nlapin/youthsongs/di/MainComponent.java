package com.nlapin.youthsongs.di;

import com.nlapin.youthsongs.data.SongRepository;
import com.nlapin.youthsongs.data.local.AppDatabase;
import com.nlapin.youthsongs.ui.homescreen.HomeViewModel;
import com.nlapin.youthsongs.ui.songscreen.SongViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SongDaoModule.class, ApplicationModule.class, CloudModule.class})
public interface MainComponent {
    AppDatabase getAppDatabase();

    void inject(SongRepository firestoreHelper);

    void inject(HomeViewModel homeViewModel);

    void inject(SongViewModel songViewModel);
}
