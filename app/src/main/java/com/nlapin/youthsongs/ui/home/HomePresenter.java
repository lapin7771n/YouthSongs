package com.nlapin.youthsongs.ui.home;

import com.nlapin.youthsongs.ui.BasePresenter;
import com.nlapin.youthsongs.data.SongsRepository;
import com.nlapin.youthsongs.models.Song;

import java.util.List;

public class HomePresenter
        extends BasePresenter<HomeContract.View>
        implements HomeContract.Presenter {

    private SongsRepository songsRepository;

    HomePresenter(SongsRepository songsRepository) {
        this.songsRepository = songsRepository;
    }

    @Override
    public void loadData() {
        List<Song> songs = songsRepository.getAll();
        getView().showSongs(songs);
    }

    @Override
    public Song onItemClick(int position) {
        return songsRepository.getByID(position);
    }

    @Override
    public void showError() {
    }
}
