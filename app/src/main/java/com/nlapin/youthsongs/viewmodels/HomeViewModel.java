package com.nlapin.youthsongs.viewmodels;

import android.util.Log;

import com.nlapin.youthsongs.models.AuthorSelectionUI;
import com.nlapin.youthsongs.models.Song;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";

    private MutableLiveData<List<Song>> songs;
    private MutableLiveData<List<AuthorSelectionUI>> authorsSelection;

    public MutableLiveData<List<Song>> getSongs() {
        Log.d(TAG, "getSongs: ");
        if (songs == null) {
            songs = new MutableLiveData<>();
            loadSongs();
        }
        return songs;
    }

    private void loadSongs() {
        Log.d(TAG, "loadSongs: ");
    }


}
