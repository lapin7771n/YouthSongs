package com.nlapin.youthsongs.data;

import com.nlapin.youthsongs.models.AuthorsSelectionPOJO;
import com.nlapin.youthsongs.models.FavoriteSong;
import com.nlapin.youthsongs.models.Song;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AuthorsSelectionPOJO.class, Song.class, FavoriteSong.class}, version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AuthorsSelectionDao authorsSelectionDao();

    public abstract SongDao songDao();

    public abstract FavoriteSongDao favoriteSongDao();
}
