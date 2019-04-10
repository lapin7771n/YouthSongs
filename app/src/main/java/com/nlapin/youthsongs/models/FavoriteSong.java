package com.nlapin.youthsongs.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_song_table")
public class FavoriteSong {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long songId;

    public FavoriteSong(long songId) {
        this.songId = songId;
    }

    public long getId() {
        return id;
    }

    public long getSongId() {
        return songId;
    }

    public void setId(long id) {
        this.id = id;
    }
}
