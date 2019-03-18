package com.nlapin.youthsongs.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.models.Song;

import java.util.ArrayList;
import java.util.List;

public class SongsRepositoryImpl
        implements SongsRepository {

    private DataBaseHelper dataBaseHelper;

    private static final String TABLE_SONGS = "Songs";

    private static final String KEY_ID = "Number";
    private static final String KEY_NAME = "Name";
    private static final String KEY_TEXT = "Text";
    private static final String KEY_CHORUS = "Chorus";


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

}
