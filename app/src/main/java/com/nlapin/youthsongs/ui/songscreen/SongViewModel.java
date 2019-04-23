package com.nlapin.youthsongs.ui.songscreen;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.SongRepository;
import com.nlapin.youthsongs.data.remote.FirebaseStorageHelper;
import com.nlapin.youthsongs.models.Song;

import javax.inject.Inject;

public class SongViewModel extends ViewModel {

    @Inject
    SongRepository songRepository;

    @Inject
    FirebaseStorageHelper storageHelper;



    public SongViewModel() {
        YouthSongsApp.getComponent().inject(this);
    }

    public LiveData<Song> getSongById(int id) {
        return songRepository.geyById(id);
    }



}
