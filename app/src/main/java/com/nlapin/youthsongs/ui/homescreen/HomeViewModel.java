package com.nlapin.youthsongs.ui.homescreen;

import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.SongRepository;
import com.nlapin.youthsongs.models.Song;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.Flowable;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";

    @Inject
    SongRepository songRepository;

    public HomeViewModel() {
        YouthSongsApp.getComponent().inject(this);
    }

    public Flowable<List<Song>> getSongs() {
        return songRepository.getAll();
    }
}
