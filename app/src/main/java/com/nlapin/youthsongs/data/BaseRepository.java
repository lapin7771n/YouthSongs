package com.nlapin.youthsongs.data;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface BaseRepository<T> {

    LiveData<List<T>> getAll();

    LiveData<T> geyById(int id);

    void delete(T t);

    void update(T t);
}
