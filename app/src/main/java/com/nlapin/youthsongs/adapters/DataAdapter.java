package com.nlapin.youthsongs.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nlapin.youthsongs.model.DataBaseHelper;
import com.nlapin.youthsongs.model.Song;
import com.nlapin.youthsongs.model.SongsDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataAdapter implements SongsDAO {

    public static List<Song> cachedSongs = null;

    private SQLiteDatabase database;

    private static final String TABLE_NAME = "Songs";

    private static final String FIELD_ID = "Number";
    private static final String FIELD_NAME = "Name";
    private static final String FIELD_TEXT = "Text";

    public DataAdapter(Context context) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);

        try {
            dataBaseHelper.createDataBase();
        } catch (IOException e) {
            throw new Error("Can`t create DB");
        }

        database = dataBaseHelper.getReadableDatabase();
        cachedSongs = getAll();
    }

    @Override
    public List<Song> getAll() {
        ArrayList<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(FIELD_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_NAME));
            String text = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_TEXT));

            Song song = new Song(id, name, text);
            songs.add(song);
        }

        cursor.close();

        return songs;
    }

    @Override
    public Song getById(int songID) {
        Song song = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=" + songID;
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(FIELD_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_NAME));
            String text = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_TEXT));

            song = new Song(id, name, text);

        }

        cursor.close();
        return song;
    }
}
