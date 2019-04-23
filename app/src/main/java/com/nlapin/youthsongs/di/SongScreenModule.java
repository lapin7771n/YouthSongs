package com.nlapin.youthsongs.di;

import com.nlapin.youthsongs.data.remote.FirebaseStorageHelper;
import com.nlapin.youthsongs.network.NetworkService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SongScreenModule {

    @Singleton
    @Provides
    static NetworkService provideNetworkService() {
        return new NetworkService();
    }

    @Singleton
    @Provides
    static FirebaseStorageHelper provideFireStorageHelper() {
        return new FirebaseStorageHelper();
    }
}
