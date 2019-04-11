package com.nlapin.youthsongs.di;

import com.nlapin.youthsongs.data.AppDatabase;
import com.nlapin.youthsongs.data.firebase.FirestoreHelper;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SongDaoModule.class, ApplicationModule.class})
public interface MainComponent {
    AppDatabase getAppDatabase();

    FirestoreHelper getFirestoreHelper();
}
