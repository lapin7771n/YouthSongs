package com.nlapin.youthsongs.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.common.base.Functions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.local.SongDao;
import com.nlapin.youthsongs.data.remote.SongCloudRepository;
import com.nlapin.youthsongs.models.Song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;

public class SongRepository implements BaseRepository<Song> {
    private static final String TAG = "SongRepository";

    @Inject
    SongDao songDao;

    @Inject
    SongCloudRepository songCloudRepository;

    public SongRepository() {
        YouthSongsApp.getComponent().inject(this);
    }

    @Override
    public LiveData<List<Song>> getAll() {
        MutableLiveData<List<Song>> liveData = new MutableLiveData<>();
        songDao.getAll().observeForever(liveData::setValue);

        songCloudRepository.provideAllSongs(songs -> {
            if (songs != null) {
                liveData.setValue(songs);
                cacheToLocalDatabase(songs);
            }
        });

        return liveData;
    }

    @Override
    public LiveData<Song> geyById(int id) {
        final MutableLiveData<Song> song = new MutableLiveData<>();

        new Thread(() -> {
            final Song localSong = songDao.getById(id);
            song.postValue(localSong);

            if (localSong == null) {
                songCloudRepository.provideSongById(id, remoteSong -> {
                    if (remoteSong != null) {
                        song.postValue(remoteSong);
                        Log.i(TAG, "Song loaded from the server! " + remoteSong.toString());
                    }
                });
            }
        }).start();

        return song;
    }

    public LiveData<List<Song>> getAllByIds(List<Integer> ids) {
        Collections.sort(ids);
        ArrayList<String> idsString = Lists.newArrayList(Iterables.transform(ids, Functions.toStringFunction()));
        return songDao.getByIds(idsString);
    }

    @Override
    public void delete(Song song) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Song song) {
        new Thread(() -> {
            songCloudRepository.updateSong(song);
            songDao.insertAll(song);
        }).start();
    }

    private void cacheToLocalDatabase(List<Song> songs) {
        new Thread(() -> songDao.insertAll(songs.toArray(new Song[0]))).start();
    }
}
