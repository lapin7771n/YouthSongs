package com.nlapin.youthsongs.data;

import com.nlapin.youthsongs.models.AuthorsSelectionPOJO;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AuthorsSelectionDao {

    @Query("SELECT * FROM authors_choise_table")
    LiveData<List<AuthorsSelectionPOJO>> getAll();

    @Query("SELECT * FROM authors_choise_table WHERE id LIKE :id")
    AuthorsSelectionPOJO getById(int id);

    @Insert
    void insertAll(AuthorsSelectionPOJO... authorsSelectionPOJOS);

    @Delete
    void delete(AuthorsSelectionPOJO authorsSelectionPOJO);
}
