package com.nlapin.youthsongs.ui.favsongscreen;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.SongRepository;
import com.nlapin.youthsongs.data.local.FavoriteSongDao;
import com.nlapin.youthsongs.models.FavoriteSong;
import com.nlapin.youthsongs.models.Song;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavoritesViewModel extends ViewModel {

    private static final String TAG = "FavoritesViewModel";

    @Inject
    FavoriteSongDao favoriteSongDao;

    @Inject
    SongRepository songRepository;

    public FavoritesViewModel() {
        YouthSongsApp.getComponent().inject(this);
    }

    LiveData<List<FavoriteSong>> getAllFavoriteSongs() {
        return favoriteSongDao.getAll();
    }

    Observable<LiveData<List<Song>>> getAllSong(List<FavoriteSong> favoriteSongs) {

        return Observable
                .create((ObservableEmitter<LiveData<List<Song>>> emitter) -> {
                    ArrayList<Integer> ids = new ArrayList<>();

                    for (FavoriteSong favoriteSong : favoriteSongs) {
                        ids.add((int) favoriteSong.getSongId());
                    }

                    LiveData<List<Song>> songs = songRepository.getAllByIds(ids);
                    Log.d(TAG, "getAllSong: count - " + ids.size());
                    emitter.onNext(songs);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
