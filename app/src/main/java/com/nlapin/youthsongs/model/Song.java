package com.nlapin.youthsongs.model;

public class Song implements Comparable<Song> {
    private final long id;
    private boolean favorite;
    private final String name;
    private final String text;
    private final String chorus;

    public Song(long id, String name, String text, String chorus) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.chorus = chorus;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    /**
     * Give you structured HTML code of song
     * @return Song text in HTML
     */
    public String getSongText() {
        final String CHORUS_MARKER = "[Chorus]";
        final String CHORUS_REPEAT_MARKER = "[rChorus]";
        String songText = text;
        songText = songText.replace(CHORUS_MARKER, "<b>" + chorus + "</b>");
        songText = songText.replace(CHORUS_REPEAT_MARKER, "<b>" + chorus + "</b>");
        songText = songText.replace("\n", "<br>");
        return songText;
    }

    @Override
    public int compareTo(Song o) {
        return Long.compare(this.getId(), o.getId());
    }
}
