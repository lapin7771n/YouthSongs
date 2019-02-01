package com.nlapin.youthsongs.di.app;

import android.content.Context;

import com.nlapin.youthsongs.data.DataBaseHelper;
import com.nlapin.youthsongs.data.FavoriteSongsRepository;
import com.nlapin.youthsongs.data.SongsRepository;

public interface AppDI {

    Context provideContext();

    DataBaseHelper provideDBHelper();

    FavoriteSongsRepository provideFavoriteSongRepository();

    SongsRepository provideSongsRepository();
}
