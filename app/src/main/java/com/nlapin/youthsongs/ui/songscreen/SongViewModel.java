package com.nlapin.youthsongs.ui.songscreen;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.FavoritesRepository;
import com.nlapin.youthsongs.data.SongRepository;
import com.nlapin.youthsongs.data.local.FavoriteSongDao;
import com.nlapin.youthsongs.data.remote.FirebaseStorageHelper;
import com.nlapin.youthsongs.models.FavoriteSong;
import com.nlapin.youthsongs.models.Song;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SongViewModel extends ViewModel {

    private static final String TAG = "SongViewModel";

    @Inject
    SongRepository songRepository;

    @Inject
    FirebaseStorageHelper storageHelper;

    @Inject
    FavoriteSongDao favoriteSongDao;

    @Inject
    FavoritesRepository favoritesRepository;


    public SongViewModel() {
        YouthSongsApp.getComponent().inject(this);
    }

    LiveData<Song> getSongById(int id) {
        return songRepository.geyById(id);
    }

    void saveToFavorites(int id) {
        new Thread(() -> favoriteSongDao.insertAll(new FavoriteSong(id))).start();
    }

    void deleteFromFavorites(int id) {
        Completable.fromAction(() -> {
            int delete = favoriteSongDao.delete(id);
            Log.d(TAG, "deleteFromFavorites: items - " + delete);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    Flowable<FavoriteSong> isFavorite(int songId) {
        return favoritesRepository.getById(songId);
    }
}
