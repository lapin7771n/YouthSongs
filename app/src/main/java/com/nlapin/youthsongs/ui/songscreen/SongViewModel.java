package com.nlapin.youthsongs.ui.songscreen;

import androidx.lifecycle.ViewModel;

import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.FavoritesRepository;
import com.nlapin.youthsongs.data.SongRepository;
import com.nlapin.youthsongs.models.FavoriteSong;
import com.nlapin.youthsongs.models.Song;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

public final class SongViewModel extends ViewModel {

    private static final String TAG = "SongViewModel";

    @Inject
    SongRepository songRepository;

    @Inject
    FavoritesRepository favoritesRepository;


    public SongViewModel() {
        YouthSongsApp.getComponent().inject(this);
    }

    Maybe<Song> getSongById(int id) {
        return songRepository.geyById(id);
    }

    void saveToFavorites(int id) {
        favoritesRepository.insert(new FavoriteSong(id));
    }

    void deleteFromFavorites(int id) {
        favoritesRepository.delete(id);
    }

    Flowable<FavoriteSong> isFavorite(int songId) {
        return favoritesRepository.getById(songId);
    }
}
