package com.nlapin.youthsongs.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nlapin.youthsongs.models.Song;

import java.util.ArrayList;
import java.util.List;


public class FavoriteSongsRepositoryImpl
        implements FavoriteSongsRepository {

    private DataBaseHelper dataBaseHelper;
    private SongsRepository songsRepository;

    private static List<Song> cache = new ArrayList<>();

    private static final String TABLE_FAVORITE_SONGS = "FavoriteSongs";

    private static final String KEY_SONG_ID = "song_id";


    public FavoriteSongsRepositoryImpl(DataBaseHelper dataBaseHelper,
                                       SongsRepository songsRepository) {
        this.dataBaseHelper = dataBaseHelper;
        this.songsRepository = songsRepository;
        loadCache();
    }

    /**
     * Add song to database [Favorite songs table]
     *
     * @param songID unique identifier os song
     */
    @Override
    public void addToFavorite(int songID) {
        ContentValues values = new ContentValues();
        values.put(KEY_SONG_ID, songID);

        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        db.insert(TABLE_FAVORITE_SONGS, null, values);

        loadCache();
    }

    /**
     * Delete song by it id from [Favorite songs table]
     *
     * @param songID unique identifier of song
     */
    @Override
    public void deleteFromFavorite(int songID) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        db.delete(TABLE_FAVORITE_SONGS,
                KEY_SONG_ID + " = ?",
                new String[]{String.valueOf(songID)});

        loadCache();
    }

    /**
     * Work with cached song for higher performance
     *
     * @return cached favorite songs id
     */
    @Override
    public List<Song> getAll() {
        return cache;
    }

    /**
     * Work with cached song for higher performance
     *
     * @param id unique identifier of favorite song
     * @return id of REGULAR song
     */
    @Override
    public Song getByID(int id) {
        return cache.get(++id);
    }

    private void loadCache() {
        ArrayList<Integer> favoriteSongsID = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_FAVORITE_SONGS;

        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int songID = cursor.getInt(
                    cursor.getColumnIndexOrThrow(KEY_SONG_ID)
            );
            favoriteSongsID.add(songID);
        }

        ArrayList<Song> favoriteSongs = new ArrayList<>();
        for (Integer integer : favoriteSongsID) {
            favoriteSongs.add(songsRepository.getByID(integer));
        }

        cursor.close();
        refreshCache(favoriteSongs);
    }

    /**
     * Setting new cache
     * Invokes after add/delete table operations
     *
     * @param newCache latest favorite songs in Table
     */
    private void refreshCache(List<Song> newCache) {
        cache.clear();
        cache.addAll(newCache);
    }
}
