package com.nlapin.youthsongs.models;

public class Song implements Comparable<Song> {
    private final int id;
    private final String name;
    private final String text;
    private final String chorus;
    //private boolean hasChords;

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

    /**
     * Give you structured HTML code of song
     *
     * @return Song text with HTML tags
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

    public String getText() {
        return text;
    }

    public String getChorus() {
        return chorus;
    }

    // TODO: 10.03.19 implement this method
    public String getSongTextWithoutChords(){
        throw new UnsupportedOperationException();
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
