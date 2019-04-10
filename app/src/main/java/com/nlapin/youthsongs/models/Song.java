package com.nlapin.youthsongs.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "song_table")
public class Song implements Comparable<Song> {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String name;
    private final String text;
    private final String chorus;

    public Song(int id, String name, String text, String chorus) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.chorus = chorus;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getChorus() {
        return chorus;
    }

    @Override
    public int compareTo(Song o) {
        return Long.compare(this.getId(), o.getId());
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
