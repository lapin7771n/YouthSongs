package com.nlapin.youthsongs.utils;

import com.nlapin.youthsongs.models.Song;

public class SongUtils {

    /**
     * Give you structured HTML code of song
     *
     * @return Song text with HTML tags
     */
    public static String getSongTextFormated(Song song) {
        final String CHORUS_MARKER = "[Chorus]";
        final String CHORUS_REPEAT_MARKER = "[rChorus]";
        String songText = song.getText();
        songText = songText.replace(CHORUS_MARKER, "<b>" + song.getChorus() + "</b>");
        songText = songText.replace(CHORUS_REPEAT_MARKER, "<b>" + song.getChorus() + "</b>");
        songText = songText.replace("\n", "<br>");
        return songText;
    }
}

