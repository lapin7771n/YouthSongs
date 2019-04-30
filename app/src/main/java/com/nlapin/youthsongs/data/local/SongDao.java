package com.nlapin.youthsongs.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nlapin.youthsongs.models.Song;

import java.util.List;

/**
 * Interface for working with song table
 * Implements by Room
 */
@Dao
public interface SongDao {

    /**
     * Getting all song from <code>song_table</code>
     *
     * @return all songs in the song_table
     */
    @Query("SELECT * FROM song_table")
    LiveData<List<Song>> getAll();

    /**
     * Getting song by id in songs table
     *
     * @param id unique identifier of song
     * @return requested song
     */
    @Query("SELECT * FROM song_table WHERE id =:id")
    Song getById(int id);

    /**
     * Inserts song to song table
     *
     * @param songs songs to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Song... songs);

    /**
     * Deleting song if some matches with method parameter
     *
     * @param song instance of song to delete
     */
    @Delete
    void delete(Song song);

    @Query("SELECT * FROM song_table WHERE id IN (:ids)")
    LiveData<List<Song>> getByIds(List<String> ids);
}
