package com.nlapin.youthsongs.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "song_table")
public class Song implements Comparable<Song> {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String name;
    private final String text;
    private final String chorus;
    private String coverUrlSmall;

    public Song(int id, String name, String text, String chorus) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.chorus = chorus;
    }

    public String getCoverUrlSmall() {
        return coverUrlSmall;
    }

    public void setCoverUrlSmall(String coverUrlSmall) {
        this.coverUrlSmall = coverUrlSmall;
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

    @NonNull
    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", chorus='" + chorus + '\'' +
                ", coverUrlSmall='" + coverUrlSmall + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id == song.id &&
                name.equals(song.name) &&
                text.equals(song.text) &&
                Objects.equals(chorus, song.chorus) &&
                Objects.equals(coverUrlSmall, song.coverUrlSmall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, text, chorus, coverUrlSmall);
    }
}
