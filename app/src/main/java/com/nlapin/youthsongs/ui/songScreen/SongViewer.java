package com.nlapin.youthsongs.ui.songScreen;

import android.content.Context;
import android.content.SharedPreferences;

import com.nlapin.youthsongs.models.Song;
import com.nlapin.youthsongs.ui.GlobalPreferenceConstants;

/**
 * @author nikita on 10,March,2019
 */
public class SongViewer {

    public static String getSongText(Song song, Context context) {
        SharedPreferences preferences = context
                .getSharedPreferences(GlobalPreferenceConstants.SETTINGS_KEY, Context.MODE_PRIVATE);

        boolean showChords = preferences
                .getBoolean(GlobalPreferenceConstants.SHOW_CHORDS_KEY, false);

        return (showChords) ? song.getSongText() : song.getSongTextWithoutChords();
    }
}
