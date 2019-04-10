package com.nlapin.youthsongs.data;

import com.nlapin.youthsongs.models.FavoriteSong;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FavoriteSongDao {

    @Query("SELECT * FROM favorite_song_table")
    LiveData<List<FavoriteSong>> getAll();

    @Query("SELECT * FROM favorite_song_table WHERE id =:id")
    FavoriteSong getById(int id);

    @Insert
    void insertAll(FavoriteSong... favoriteSongs);

    @Delete
    void delete(FavoriteSong favoriteSong);
}
