package com.nlapin.youthsongs.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nlapin.youthsongs.models.FavoriteSong;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface FavoriteSongDao {

    @Query("SELECT * FROM favorite_song_table")
    LiveData<List<FavoriteSong>> getAll();

    @Query("SELECT * FROM favorite_song_table WHERE id =:id")
    FavoriteSong getById(int id);

    @Query("SELECT * FROM favorite_song_table WHERE songId =:songId")
    FavoriteSong getBySongId(int songId);

    @Insert
    void insertAll(FavoriteSong... favoriteSongs);

    @Query("DELETE FROM favorite_song_table WHERE songId = :songID")
    int delete(int songID);
}
