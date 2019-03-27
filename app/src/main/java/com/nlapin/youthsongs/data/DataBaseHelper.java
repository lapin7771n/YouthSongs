package com.nlapin.youthsongs.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "songs";

    //Table names
    private static final String TABLE_FAVORITE_SONGS = "FavoriteSongs";
    private static final String TABLE_SONGS = SongsRepositoryImpl.TABLE_SONGS;

    //Songs table columns
    private static final String KEY_SONG_NUMBER = SongsRepositoryImpl.KEY_ID;
    private static final String KEY_SONG_NAME = SongsRepositoryImpl.KEY_NAME;
    private static final String KEY_SONG_TEXT = SongsRepositoryImpl.KEY_TEXT;
    private static final String KEY_SONG_CHORUS = SongsRepositoryImpl.KEY_CHORUS;

    //Favorite songs table columns
    private static final String KEY_FAV_SONGS_ID = "id";
    private static final String KEY_SONG_ID = "song_id";

    private static final String CREATE_FAVORITE_SONGS_TABLE =
            "CREATE TABLE "
                    + TABLE_FAVORITE_SONGS + " ("
                    + KEY_SONG_NUMBER + " INTEGER PRIMARY KEY, "
                    + KEY_SONG_NAME + " TEXT NOT NULL, " +
                    KEY_SONG_TEXT + " TEXT NOT NULL, " +
                    KEY_SONG_CHORUS + " TEXT)";

    private static final String CREATE_SONGS_TABLE =
            "CREATE TABLE "
                    + TABLE_SONGS + " ("
                    + KEY_FAV_SONGS_ID + " INTEGER PRIMARY KEY, "
                    + KEY_SONG_ID + " INTEGER)";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SONGS_TABLE);
        db.execSQL(CREATE_FAVORITE_SONGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new UnsupportedOperationException();
    }
}
