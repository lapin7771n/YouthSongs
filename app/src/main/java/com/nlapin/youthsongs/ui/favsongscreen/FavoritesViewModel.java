package com.nlapin.youthsongs.ui.favsongscreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.SongRepository;
import com.nlapin.youthsongs.data.local.FavoriteSongDao;
import com.nlapin.youthsongs.models.FavoriteSong;
import com.nlapin.youthsongs.models.Song;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

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

    Flowable<List<Song>> getAllSong(List<FavoriteSong> favoriteSongs) {
        return songRepository.getAllByIds(favoriteSongs);
    }
}
