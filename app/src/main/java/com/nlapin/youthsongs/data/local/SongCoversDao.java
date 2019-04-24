package com.nlapin.youthsongs.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.nlapin.youthsongs.models.PixelsResponseModel;

import java.util.List;

//@Dao
public interface SongCoversDao {

    @Query("SELECT * FROM favorite_song_table")
    LiveData<List<PixelsResponseModel>> getAll();

    @Query("SELECT * FROM favorite_song_table WHERE id =:id")
    PixelsResponseModel getById(int id);

    @Insert
    void insertAll(PixelsResponseModel... pixelsResponseModels);

    @Delete
    void delete(PixelsResponseModel pixelsResponseModel);
}
