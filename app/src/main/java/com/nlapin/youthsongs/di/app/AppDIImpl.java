package com.nlapin.youthsongs.di.app;

import android.content.Context;

import com.nlapin.youthsongs.data.DataBaseHelper;
import com.nlapin.youthsongs.data.FavoriteSongsRepository;
import com.nlapin.youthsongs.data.FavoriteSongsRepositoryImpl;
import com.nlapin.youthsongs.data.SongsRepository;
import com.nlapin.youthsongs.data.SongsRepositoryImpl;

public class AppDIImpl implements AppDI {

    private Context context;

    public AppDIImpl(Context context) {
        this.context = context;
    }

    @Override
    public Context provideContext() {
        return context;
    }

    @Override
    public DataBaseHelper provideDBHelper() {
        return new DataBaseHelper(provideContext());
    }

    @Override
    public FavoriteSongsRepository provideFavoriteSongRepository() {
        return new FavoriteSongsRepositoryImpl(provideDBHelper(), provideSongsRepository());
    }

    @Override
    public SongsRepository provideSongsRepository() {
        return new SongsRepositoryImpl(provideDBHelper());
    }
}
