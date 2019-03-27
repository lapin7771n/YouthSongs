package com.nlapin.youthsongs.ui.favsonglist;

import com.nlapin.youthsongs.ui.BasePresenter;
import com.nlapin.youthsongs.data.FavoriteSongsRepository;
import com.nlapin.youthsongs.models.Song;

import java.util.List;

public class FavoritesPresenter
        extends BasePresenter<FavoritesContract.View>
        implements FavoritesContract.Presenter {

    private FavoriteSongsRepository favoriteSongsRepository;

    FavoritesPresenter(FavoriteSongsRepository favoriteSongsRepository) {
        this.favoriteSongsRepository = favoriteSongsRepository;
    }

    @Override
    public void loadData() {
        List<Song> favoriteSongs = favoriteSongsRepository.getAll();
        if (favoriteSongs.size() > 0) {
            getView().showFavoriteSongs(favoriteSongs);
        } else {
            getView().showEmpty();
        }
        getView().hideProgressBar();
    }

    @Override
    public void onItemClick() {

    }

    @Override
    public void showError() {

    }
}
