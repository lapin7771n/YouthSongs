package com.nlapin.youthsongs.ui.songscreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.SongRepository;
import com.nlapin.youthsongs.data.local.FavoriteSongDao;
import com.nlapin.youthsongs.data.remote.FirebaseStorageHelper;
import com.nlapin.youthsongs.models.FavoriteSong;
import com.nlapin.youthsongs.models.Song;

import javax.inject.Inject;

public class SongViewModel extends ViewModel {

    @Inject
    SongRepository songRepository;

    @Inject
    FirebaseStorageHelper storageHelper;

    @Inject
    FavoriteSongDao favoriteSongDao;


    public SongViewModel() {
        YouthSongsApp.getComponent().inject(this);
    }

    public LiveData<Song> getSongById(int id) {
        return songRepository.geyById(id);
    }

    public void saveToFavorites(int id) {
        new Thread(() -> favoriteSongDao.insertAll(new FavoriteSong(id)));
    }

    public void deleteFromFavorites(int id) {
        new Thread(() -> favoriteSongDao.delete(new FavoriteSong(id)));
    }
}
