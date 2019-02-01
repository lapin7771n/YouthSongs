package com.nlapin.youthsongs.ui.home;

import android.util.Log;

import com.nlapin.youthsongs.BasePresenter;
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
        getView().showProgressBar();

        List<Song> songs = songsRepository.getAll();
        Log.d("dd", "loadData: songsCount: " + songs.size());
        getView().showSongs(songs);

        getView().hideProgressBar();
    }

    @Override
    public Song onItemClick(int position) {
        Log.d("dd", "onItemClick: pos: " + position);
        return songsRepository.getByID(position);
    }

    @Override
    public void showError() {
    }
}
