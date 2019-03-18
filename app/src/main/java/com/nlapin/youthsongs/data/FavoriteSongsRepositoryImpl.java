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

    private static final String TABLE_FAVORITE_SONGS = "FavoriteSongs";

    private static final String KEY_SONG_ID = "song_id";


    public FavoriteSongsRepositoryImpl(DataBaseHelper dataBaseHelper,
                                       SongsRepository songsRepository) {
        this.dataBaseHelper = dataBaseHelper;
        this.songsRepository = songsRepository;
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
    }

    @Override
    public List<Song> getAll() {
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
        return favoriteSongs;
    }

    @Override
    public Song getByID(int id) {
        Song song = null;

        String sql = "SELECT * FROM " + TABLE_FAVORITE_SONGS + " WHERE " + KEY_SONG_ID + "=" + id;

        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int songID = cursor.getInt(
                    cursor.getColumnIndexOrThrow(KEY_SONG_ID)
            );
            song = songsRepository.getByID(songID);

        }

        cursor.close();
        return song;
    }
}
