package com.nlapin.youthsongs.model;

import java.util.List;

public interface SongsDAO {
    List<Song> getAll(String tableName);

    Song getById(int songID);
}
