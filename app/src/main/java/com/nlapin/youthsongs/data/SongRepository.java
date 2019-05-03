package com.nlapin.youthsongs.data;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.common.base.Functions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.local.SongDao;
import com.nlapin.youthsongs.data.remote.SongCloudRepository;
import com.nlapin.youthsongs.models.FavoriteSong;
import com.nlapin.youthsongs.models.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class SongRepository {
    private static final String TAG = "SongRepository";

    @Inject
    SongDao songDao;

    @Inject
    SongCloudRepository songCloudRepository;

    public SongRepository() {
        YouthSongsApp.getComponent().inject(this);
    }

    @SuppressLint("CheckResult")
    public final Flowable<List<Song>> getAll() {
        return Flowable.create(emitter ->
                songDao.getAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe(songs -> {
                            if (songs != null && !songs.isEmpty()) {
                                emitter.onNext(songs);
                            } else {
                                songCloudRepository.provideAllSongs()
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(Schedulers.io())
                                        .subscribe(songsFromServer -> {
                                            emitter.onNext(songsFromServer);
                                            cacheToLocalDatabase(songsFromServer);
                                        }, emitter::onError, emitter::onComplete);
                            }
                        }, emitter::onError, emitter::onComplete), BackpressureStrategy.BUFFER);
    }

    public final Maybe<Song> geyById(int id) {
        return Maybe.create((MaybeOnSubscribe<Song>) emitter ->
                songDao.getById(id).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((song -> {
                            if (song == null) {
                                Log.e(TAG, "Song doesn't exist in local database!");
                                songCloudRepository.provideSongById(id)
                                        .subscribe(
                                                emitter::onSuccess,
                                                emitter::onError,
                                                emitter::onComplete)
                                        .dispose();
                            } else {
                                emitter.onSuccess(song);
                            }
                        }), emitter::onError, emitter::onComplete)
                        .dispose())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public final Flowable<List<Song>> getAllByIds(List<FavoriteSong> favoriteSongs) {

        ArrayList<Integer> ids = new ArrayList<>();

        for (FavoriteSong favoriteSong : favoriteSongs) {
            ids.add((int) favoriteSong.getSongId());
        }

        Collections.sort(ids);
        ArrayList<String> idsString = Lists.newArrayList(Iterables.transform(ids, Functions.toStringFunction()));
        return songDao.getByIds(idsString);
    }

    public final void update(Song song) {
        Completable.fromAction(() -> {
            songCloudRepository.updateSong(song);
            songDao.insertAll(song);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void cacheToLocalDatabase(List<Song> songs) {
        Completable.fromAction(() -> songDao.insertAll(songs.toArray(new Song[0])))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
