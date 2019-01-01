package com.nlapin.youthsongs.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nlapin.youthsongs.model.DataBaseHelper;
import com.nlapin.youthsongs.model.FavoriteDBHelper;
import com.nlapin.youthsongs.model.Song;
import com.nlapin.youthsongs.model.SongsDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class DataAdapter implements SongsDAO {

    public static List<Song> cachedSongs = new ArrayList<>();
    public static List<Song> favoriteSongs = new ArrayList<>();

    private SQLiteDatabase database;

    private Context context;

    private static final String DEFAULT_TABLE = "Songs";

    private static final String FIELD_ID = "Number";
    private static final String FIELD_NAME = "Name";
    private static final String FIELD_TEXT = "Text";
    private static final String FIELD_CHORUS = "Chorus";

    public DataAdapter(Context context) {
        this.context = context;

        initCache();
        favoriteSongs = new ArrayList<>(getAllFromFavorites());
    }

    public void initCache() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);

        try {
            dataBaseHelper.createDataBase();
            dataBaseHelper.updateDataBase();
        } catch (IOException e) {
            throw new Error("Can`t create DB");
        }
        database = dataBaseHelper.getReadableDatabase();
        cachedSongs = getAll(DEFAULT_TABLE);
    }

    /**
     * Get all records from DB
     * @return List of Song (ArrayList)
     */
    @Override
    public List<Song> getAll(String tableName) {
        ArrayList<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(FIELD_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_NAME));
            String text = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_TEXT));
            String chorus = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_CHORUS));

            Song song = new Song(id, name, text, chorus);
            songs.add(song);
        }

        cursor.close();

        return songs;
    }

    /**
     * Get Song from DB by ID
     * @param songID (Required Song id)
     * @return Song
     */
    @Override
    public Song getById(int songID) {
        Song song = null;
        String sql = "SELECT * FROM " + DEFAULT_TABLE + " WHERE id=" + songID;
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(FIELD_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_NAME));
            String text = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_TEXT));
            String chorus = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_CHORUS));

            song = new Song(id, name, text, chorus);

        }

        cursor.close();
        return song;
    }

    /**
     * Binary search algorithm
     * @param songID (required Song songID)
     * @return Song
     */
    public static Song getByIdInCache(int songID) {
        Song song = null;
        int start = 0;
        int lastElem = cachedSongs.size();
        int end = (int) cachedSongs.get(lastElem - 1).getId();
        int mid;

        while (start < end) {
            mid = (end + start) / 2;
            Song iterateSong = cachedSongs.get(mid);
            if (iterateSong.getId() == songID) {
                song = iterateSong;
                break;
            } else {
                if (songID < iterateSong.getId()) {
                    end = mid;
                } else {
                    start = mid;
                }
            }
        }

        return song;
    }

    public boolean addToFavorite(int id) {
        return new FavoriteDBHelper(context).create(id);
    }

    public boolean removeFromFavorite(int id) {
        return new FavoriteDBHelper(context).delete(id);
    }

    public ArrayList<Song> getAllFromFavorites() {
        ArrayList<Song> songsSet = new ArrayList<>();
        Set<Integer> setOfIds = new HashSet<>(new FavoriteDBHelper(context).read());
        for (Integer id : setOfIds) {
            Song inCache = getByIdInCache(id);
            inCache.setFavorite(true);
            songsSet.add(inCache);
        }
        return songsSet;
    }

}
