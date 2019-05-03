package com.nlapin.youthsongs.data;

import androidx.lifecycle.LiveData;

import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.local.FavoriteSongDao;
import com.nlapin.youthsongs.models.FavoriteSong;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavoritesRepository {

    @Inject
    FavoriteSongDao favoriteSongDao;

    @Inject
    public FavoritesRepository() {
        YouthSongsApp.getComponent().inject(this);
    }

    public Observable<LiveData<List<FavoriteSong>>> getAll() {
        return Observable.just(favoriteSongDao.getAll());
    }

    public Flowable<FavoriteSong> getById(int id) {
        return favoriteSongDao.getBySongId(id);
    }

    public void delete(int songId) {
        Completable.fromAction(() -> favoriteSongDao.delete(songId))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void update(FavoriteSong favoriteSong) {
        Completable.fromAction(() -> favoriteSongDao.insertAll(favoriteSong))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void insert(FavoriteSong... favoriteSongs) {
        Completable.fromAction(() -> favoriteSongDao.insertAll(favoriteSongs))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
