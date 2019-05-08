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

/**
 * @author Nikita
 * nlapin.java@gmail.com
 */
public class FavoritesViewModel extends ViewModel {

    @Inject
    FavoriteSongDao favoriteSongDao;

    @Inject
    SongRepository songRepository;

    public FavoritesViewModel() {
        YouthSongsApp.getComponent().inject(this);
    }

    /**
     * @return LiveData list of Favorite songs from local database
     */
    LiveData<List<FavoriteSong>> getAllFavoriteSongs() {
        return favoriteSongDao.getAll();
    }

    /**
     * @param favoriteSongs list of objects that contains ids of favoriteSongs
     * @return Observable data from local songs table that contains only favorite songs
     */
    Flowable<List<Song>> getAllSong(List<FavoriteSong> favoriteSongs) {
        return songRepository.getAllByIds(favoriteSongs);
    }
}
