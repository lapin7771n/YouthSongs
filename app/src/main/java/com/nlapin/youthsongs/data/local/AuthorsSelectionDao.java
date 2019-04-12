package com.nlapin.youthsongs.data.local;

import com.nlapin.youthsongs.models.AuthorsSelectionPOJO;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * API for working with local table
 */
@Dao
public interface AuthorsSelectionDao {

    /**
     * Getting all songs from local Song table
     *
     * @return All songs in the local table
     */
    @Query("SELECT * FROM authors_choise_table")
    LiveData<List<AuthorsSelectionPOJO>> getAll();

    /***
     * Getting specified song from table
     * @param id number of requaired song
     * @return song from the table by id
     */
    @Query("SELECT * FROM authors_choise_table WHERE id LIKE :id")
    AuthorsSelectionPOJO getById(int id);

    /**
     * Inserting songs from params
     *
     * @param authorsSelectionPOJOS songs to insert
     */
    @Insert
    void insertAll(AuthorsSelectionPOJO... authorsSelectionPOJOS);

    /**
     * Deleting song that matches with songs from params
     *
     * @param authorsSelectionPOJO song to delete
     */
    @Delete
    void delete(AuthorsSelectionPOJO authorsSelectionPOJO);
}
