package com.nlapin.youthsongs.model;

import java.util.List;

public interface SongsDAO {
    List<Song> getAll();

    Song getById(int songID);
}
