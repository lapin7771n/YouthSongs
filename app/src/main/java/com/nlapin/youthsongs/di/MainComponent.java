package com.nlapin.youthsongs.di;

import com.nlapin.youthsongs.data.SongRepository;
import com.nlapin.youthsongs.data.local.AppDatabase;
import com.nlapin.youthsongs.data.remote.FirebaseStorageHelper;
import com.nlapin.youthsongs.data.remote.SongCloudRepository;
import com.nlapin.youthsongs.network.NetworkService;
import com.nlapin.youthsongs.ui.MainActivity;
import com.nlapin.youthsongs.ui.homescreen.HomeViewModel;
import com.nlapin.youthsongs.ui.songscreen.SongActivity;
import com.nlapin.youthsongs.ui.songscreen.SongViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SongDaoModule.class, ApplicationModule.class, CloudModule.class,
        SongScreenModule.class})
public interface MainComponent {
    AppDatabase getAppDatabase();

    FirebaseStorageHelper getFirestorageHelper();

    SongRepository getSongRepository();

    NetworkService getNetworkService();

    SongCloudRepository getSongCloudRepository();

    void inject(SongRepository firestoreHelper);

    void inject(HomeViewModel homeViewModel);

    void inject(SongViewModel songViewModel);

    void inject(SongActivity songActivity);

    void inject(MainActivity songActivity);
}
