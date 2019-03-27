package com.nlapin.youthsongs.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nlapin.youthsongs.models.Song;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SongsRepositoryImpl
        implements SongsRepository {

    public static final String TAG = SongsRepositoryImpl.class.getName();
    private DataBaseHelper dataBaseHelper;

    public static final String TABLE_SONGS = "Songs";

    public static final String KEY_ID = "Number";
    public static final String KEY_NAME = "Name";
    public static final String KEY_TEXT = "Text";
    public static final String KEY_CHORUS = "Chorus";


    public SongsRepositoryImpl(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }


    @Override
    public List<Song> getAll() {
        ArrayList<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_SONGS;
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(KEY_ID)
            );

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(KEY_NAME)
            );

            String text = cursor.getString(
                    cursor.getColumnIndexOrThrow(KEY_TEXT)
            );

            String chorus = cursor.getString(
                    cursor.getColumnIndexOrThrow(KEY_CHORUS)
            );

            Song song = new Song(id, name, text, chorus);
            songs.add(song);
        }
        cursor.close();
        return songs;
    }

    /**
     * @param id unique identifier of Song
     * @return requested Song
     */
    @Override
    public Song getByID(int id) {
        Song song = null;

        String sql = "SELECT * FROM " + TABLE_SONGS + " WHERE " + KEY_ID + "=" + id;
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(KEY_NAME)
            );

            String text = cursor.getString(
                    cursor.getColumnIndexOrThrow(KEY_TEXT)
            );

            String chorus = cursor.getString(
                    cursor.getColumnIndexOrThrow(KEY_CHORUS)
            );
            song = new Song(id, name, text, chorus);
        }

        cursor.close();

        return song;
    }

    @Override
    public void addSong(Song song) {
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, song.getId());
        contentValues.put(KEY_NAME, song.getName());
        contentValues.put(KEY_TEXT, song.getText());
        contentValues.put(KEY_CHORUS, song.getChorus());

        writableDatabase.insert(TABLE_SONGS, null, contentValues);

        Log.d(TAG, "Song added to table - " + TABLE_SONGS);
    }

    @Override
    public void addAll(Collection<Song> songs) {
        for (Song song : songs) {
            addSong(song);
        }
    }
}
