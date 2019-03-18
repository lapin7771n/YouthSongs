package com.nlapin.youthsongs.ui.songScreen;

import com.nlapin.youthsongs.BasePresenter;
import com.nlapin.youthsongs.data.FavoriteSongsRepository;
import com.nlapin.youthsongs.data.SongsRepository;
import com.nlapin.youthsongs.models.Song;

public class SongPresenter
        extends BasePresenter<SongContract.View>
        implements SongContract.Presenter {

    private SongsRepository songsRepository;
    private FavoriteSongsRepository favoriteSongsRepository;

    private int songID;

    private static final float CHECKED_DIRECTION = 1;
    private static final float UNCHECKED_DIRECTION = -2.5f;

    private Song song;

    private int textSize;

    SongPresenter(SongsRepository songsRepository,
                  FavoriteSongsRepository favoriteSongsRepository) {
        this.songsRepository = songsRepository;
        this.favoriteSongsRepository = favoriteSongsRepository;
    }

    @Override
    public void favoriteClicked(boolean b) {
        if (b) {
            favoriteSongsRepository.addToFavorite(songID);
        } else {
            favoriteSongsRepository.deleteFromFavorite(songID);
        }
    }

    @Override
    public void loadSong(int songID) {
        this.songID = songID;
        song = songsRepository.getByID(songID);
        getView().setSong(song);
    }

    @Override
    public void setUpFavoriteBtn() {
        Song favoriteSong = favoriteSongsRepository.getByID(songID);
        getView().setIsFavorite(favoriteSong != null);
    }

    @Override
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    @Override
    public void showError() {

    }
}
