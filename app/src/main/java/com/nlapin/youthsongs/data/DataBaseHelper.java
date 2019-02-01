package com.nlapin.youthsongs.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";

    private static final int DB_VERSION = 2;

    private static final String DB_NAME = "songs.db";

    private static String DB_PATH = "";

    //Table names
    private static final String TABLE_FAVORITE_SONGS = "FavoriteSongs";

    //Favorite songs table columns
    private static final String KEY_FAV_SONGS_ID = "id";
    private static final String KEY_SONG_ID = "song_id";

    private static final String CREATE_FAVORITE_SONGS_TABLE =
            "CREATE TABLE "
                    + TABLE_FAVORITE_SONGS + " ("
                    + KEY_FAV_SONGS_ID + " INTEGER PRIMARY KEY, "
                    + KEY_SONG_ID + " INTEGER)";

    private SQLiteDatabase database;
    private final Context context;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FAVORITE_SONGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            Log.d(TAG, "onUpgrade: ");
            try {
                updateDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_SONGS);
            onCreate(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onDowngrade: ");
        try {
            updateDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateDataBase() throws IOException {
        if (true) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists()) {
                dbFile.delete();
                Log.d(TAG, "updateDataBase: ");
            }

            copyDataBase();
        }
    }

    public void createDataBase() {
        boolean dataBaseExist = checkDataBase();
        if (!dataBaseExist) {
            getReadableDatabase();
            close();
            try {
                copyDataBase();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException {
        InputStream inputStream = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream outputStream = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public boolean openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return database != null;
    }

    @Override
    public synchronized void close() {
        if (database != null)
            database.close();
        super.close();
    }
}
