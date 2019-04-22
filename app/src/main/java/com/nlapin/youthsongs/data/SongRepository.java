package com.nlapin.youthsongs.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.local.SongDao;
import com.nlapin.youthsongs.data.remote.SongCloudRepository;
import com.nlapin.youthsongs.models.Song;

import java.util.List;

import javax.inject.Inject;

public class SongRepository implements BaseRepository<Song> {

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
                chacheToLocalDatabase(songs);
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
                    }
                });
            }
        }).start();

        return song;
    }

    @Override
    public void delete(Song song) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Song song) {

    }

    private void chacheToLocalDatabase(List<Song> songs) {
        new Thread(() -> songDao.insertAll(songs.toArray(new Song[0]))).start();
    }
}
