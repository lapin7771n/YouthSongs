package com.nlapin.youthsongs.data;

import androidx.lifecycle.LiveData;

import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.local.FavoriteSongDao;
import com.nlapin.youthsongs.models.FavoriteSong;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavoritesRepository {

    @Inject
    FavoriteSongDao favoriteSongDao;

    public FavoritesRepository() {
        YouthSongsApp.getComponent().inject(this);
    }

    public Observable<LiveData<List<FavoriteSong>>> getAll() {
        return Observable.just(favoriteSongDao.getAll())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Observable<FavoriteSong> geyById(int id) {
        return Observable.just(favoriteSongDao.getBySongId(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void delete(int songId) {
        Completable.fromAction(() -> favoriteSongDao.delete(songId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void update(FavoriteSong favoriteSong) {
        Completable.fromAction(() -> favoriteSongDao.insertAll(favoriteSong))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
