package com.nlapin.youthsongs.models;

import java.util.Arrays;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "authors_choise_table")
public class AuthorsSelectionPOJO {

    @PrimaryKey(autoGenerate = true)
    private final long id;

    private final String name;
    @TypeConverters({AuthorsSelectionConverter.class})
    private final long[] songsIds;

    public AuthorsSelectionPOJO(long id, String name, long[] songsIds) {
        this.id = id;
        this.name = name;
        this.songsIds = songsIds;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long[] getSongsIds() {
        return songsIds;
    }

    public static class AuthorsSelectionConverter {
        @TypeConverter
        public String fromSongsIds(long[] ids) {
            StringBuilder stringBuffer = new StringBuilder();
            for (long id : ids) {
                stringBuffer.append(String.valueOf(id)).append(",");
            }
            return stringBuffer.toString();
        }

        @TypeConverter
        public long[] toSongsIds(String idsStr) {
            String[] split = idsStr.split(",");
            long[] longs = new long[split.length];

            for (int i = 0; i < split.length; i++) {
                longs[i] = Long.getLong(split[i]);
            }
            return longs;
        }
    }
}
