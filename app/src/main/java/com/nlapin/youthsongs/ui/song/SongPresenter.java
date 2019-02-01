package com.nlapin.youthsongs.ui.song;

import android.util.Log;

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
        song.setFavorite(b);
        Log.d("dd", "favoriteClicked: " + b);
        if (b) {
            favoriteSongsRepository.addToFavorite(songID);
        } else {
            favoriteSongsRepository.deleteFromFavorite(songID);
        }
    }

    @Override
    public void loadSong(int songID) {
        getView().showProgressBar();

        this.songID = songID;

        song = songsRepository.getByID(songID);

        getView().setToolbar(song.getId() + " " + song.getName());
        getView().setSongTextSize(textSize);
        getView().setSongText(song.getSongText());

        getView().hideProgressBar();
    }

    @Override
    public void setUpFavoriteBtn() {
        if (song.isFavorite()) {
            getView().showFavorite();
        } else {
            getView().hideFavorite();
        }
    }

    @Override
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    @Override
    public void showError() {

    }
}
