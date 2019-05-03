package com.nlapin.youthsongs.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.nlapin.youthsongs.models.AuthorsSelectionPOJO;
import com.nlapin.youthsongs.models.FavoriteSong;
import com.nlapin.youthsongs.models.Song;

/**
 * Room database abstract class
 */
@Database(entities = {AuthorsSelectionPOJO.class, Song.class, FavoriteSong.class}, version = 2,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    /**
     * AuthorsSelection table DAO \ API
     *
     * @return local database API for table authors_selection_table
     */
    public abstract AuthorsSelectionDao authorsSelectionDao();

    /**
     * Song table DAO \ API
     *
     * @return local database API for table song_table
     */
    public abstract SongDao songDao();

    /**
     * FavoriteSongDao table DAO \ API
     *
     * @return local database API for table favorite_song_table
     */
    public abstract FavoriteSongDao favoriteSongDao();

//    /**
//     * Table for song cover. For download covers uses third-party API
//     *
//     * @return local database API for table song_covers
//     */
//    public abstract SongCoversDao songCoversDao();

}
