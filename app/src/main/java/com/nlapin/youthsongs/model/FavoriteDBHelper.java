package com.nlapin.youthsongs.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import androidx.annotation.Nullable;

public class FavoriteDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "favoriteDB";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "FavoriteSongs";

    private static final String FIELD_ID = "id";

    public FavoriteDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME +
                "( " + FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS students";
        db.execSQL(sql);

        onCreate(db);
    }

    public boolean create(int id) {
        ContentValues values = new ContentValues();

        values.put(FIELD_ID, id);

        SQLiteDatabase db = getWritableDatabase();

        boolean successful = db.insert(TABLE_NAME, null, values) > 0;
        db.close();

        return successful;
    }

    public Set<Integer> read() {

        Set<Integer> recordSet = new TreeSet<>();

        String sql = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(FIELD_ID)));
                recordSet.add(id);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordSet;
    }

    public boolean delete(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        boolean deleteSuccessful = db.delete(TABLE_NAME, "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }

}
