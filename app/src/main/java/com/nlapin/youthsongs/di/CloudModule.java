package com.nlapin.youthsongs.di;

import android.content.Context;

import com.nlapin.youthsongs.data.remote.SongCloudRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class CloudModule {

    @Provides
    static SongCloudRepository provideSongCloudRepository(Context context) {
        return new SongCloudRepository(context);
    }
}
