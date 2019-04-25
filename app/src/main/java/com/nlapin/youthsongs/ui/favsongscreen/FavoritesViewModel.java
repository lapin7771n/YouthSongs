package com.nlapin.youthsongs.ui.favsongscreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.SongRepository;
import com.nlapin.youthsongs.data.local.FavoriteSongDao;
import com.nlapin.youthsongs.models.FavoriteSong;
import com.nlapin.youthsongs.models.Song;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FavoritesViewModel extends ViewModel {

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

    LiveData<List<Song>> getAllSong(List<FavoriteSong> favoriteSongs) {
        MutableLiveData<List<Song>> songsLD = new MutableLiveData<>();
        new Thread(() -> {
            ArrayList<Song> songs = new ArrayList<>();
            for (FavoriteSong favoriteSong : favoriteSongs) {
                songRepository.geyById((int) favoriteSong.getSongId()).observeForever(songs::add);
            }
            songsLD.postValue(songs);
        });

        return songsLD;
    }
}
