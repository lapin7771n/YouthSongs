package com.nlapin.youthsongs.data;

import android.os.AsyncTask;

import com.nlapin.youthsongs.models.Song;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

public class SongRepository {

    private SongDao songDao;

    @Inject
    public SongRepository(SongDao songDao) {
        this.songDao = songDao;
    }

    public void insertSong(Song song) {
        new Thread(() -> songDao.insertAll(song)).start();
    }

    public LiveData<List<Song>> getAll() throws ExecutionException, InterruptedException {
        return new GetSongsAsyncTask().execute().get();
    }

    public Song getById(int id) throws ExecutionException, InterruptedException {
        return new GetSongByIdAsyncTask(id).execute().get();
    }

    public void delete(Song song) {
        songDao.delete(song);
    }

    private class GetSongsAsyncTask
            extends AsyncTask<Void, Void, LiveData<List<Song>>> {

        @Override
        protected LiveData<List<Song>> doInBackground(Void... voids) {
            return songDao.getAll();
        }
    }

    private class GetSongByIdAsyncTask
            extends AsyncTask<Void, Void, Song> {

        private final int id;

        public GetSongByIdAsyncTask(int id) {
            this.id = id;
        }

        @Override
        protected Song doInBackground(Void... voids) {
            return songDao.getById(id);
        }
    }
}
