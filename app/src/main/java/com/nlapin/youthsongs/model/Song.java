package com.nlapin.youthsongs.model;

public class Song {
    private final long id;
    private final String name;
    private final String text;

    public Song(long id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
