package com.nlapin.youthsongs.data;

import java.util.List;

public interface BaseSongsRepository<V> {

    List<V> getAll();

    V getByID(int id);
}
