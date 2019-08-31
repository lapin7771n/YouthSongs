package com.nlapin.youthsongs.utils;

import com.google.firebase.firestore.DocumentSnapshot;
import com.nlapin.youthsongs.models.Song;

import java.util.ArrayList;
import java.util.List;

import static com.nlapin.youthsongs.data.remote.SongCloudRepository.CHORUS_OF_SONG_KEY;
import static com.nlapin.youthsongs.data.remote.SongCloudRepository.COVER_URI_SMALL;
import static com.nlapin.youthsongs.data.remote.SongCloudRepository.NAME_OF_SONG_KEY;
import static com.nlapin.youthsongs.data.remote.SongCloudRepository.NUMBER_OF_SONG_KEY;
import static com.nlapin.youthsongs.data.remote.SongCloudRepository.TEXT_OF_SONG_KEY;

public class SongUtils {

    private SongUtils(){}

    /**
     * Give you structured HTML code of song
     *
     * @return Song text with HTML tags
     */
    public static String getSongTextFormated(Song song, boolean withChords) {
        final String CHORUS_MARKER = "[Chorus]";
        final String CHORUS_REPEAT_MARKER = "[rChorus]";
        StringBuilder songText = new StringBuilder(song.getText());
        songText = new StringBuilder(songText.toString().replace(CHORUS_MARKER, "<b>" + song.getChorus() + "</b>"));
        songText = new StringBuilder(songText.toString().replace(CHORUS_REPEAT_MARKER, "<b>" + song.getChorus() + "</b>"));
        String replacement = "<b><font color=\"#B22222\">$1</font></b>";
        String formattedText = songText.toString()
                .replaceAll("([A-H](#|b)?)(\\(?(M|maj|major|m|min|minor|dim|sus|dom|aug)?(\\+|-|add)?\\d*\\)?)(\\/([A-G](#|b)?))?",
                        (withChords) ? replacement : "");
        formattedText = formattedText.replace("\n", "<br>");
        return formattedText;
    }

    /**
     * Helper method to map Json document to SongObject
     *
     * @param documents json object
     * @return list of recieved songs
     */
    public static List<Song> mapToSongs(List<DocumentSnapshot> documents) {
        ArrayList<Song> songs = new ArrayList<>();

        //Going through all received songs
        for (DocumentSnapshot document : documents) {
            long songNumber = (long) document.get(NUMBER_OF_SONG_KEY);
            String songName = (String) document.get(NAME_OF_SONG_KEY);
            String songText = (String) document.get(TEXT_OF_SONG_KEY);
            String songChorus = (String) document.get(CHORUS_OF_SONG_KEY);
            String coverUrlSmall = (String) document.get(COVER_URI_SMALL);

            Song song = new Song((int) songNumber, songName, songText, songChorus);

            song.setCoverUrlSmall(coverUrlSmall);
            songs.add(song);
        }

        return songs;
    }
}

