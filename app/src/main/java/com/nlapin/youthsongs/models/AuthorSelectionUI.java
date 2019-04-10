package com.nlapin.youthsongs.models;

import java.util.ArrayList;
import java.util.List;

public class AuthorSelectionUI {

    private long id;

    private final String selectionName;
    private final int songsCount;
    private final List<Song> songs = new ArrayList<>();

    public AuthorSelectionUI(String selectionName, List<Song> songs) {
        this.selectionName = selectionName;
        this.songsCount = songs.size();
        this.songs.addAll(songs);
    }

    public String getSelectionName() {
        return selectionName;
    }

    public int getSongsCount() {
        return songsCount;
    }

    public long getId() {
        return id;
    }

    public List<Song> getSongs() {
        return songs;
    }
}
