package com.nlapin.youthsongs.data.firebase;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nlapin.youthsongs.data.SongsRepository;
import com.nlapin.youthsongs.models.Song;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FirestoreHelper {

    private static final String SONGS_COLLECTION = "songs";

    private static final String NAME_OF_SONG_KEY = "name";
    private static final String NUMBER_OF_SONG_KEY = "number";
    private static final String TEXT_OF_SONG_KEY = "text";
    private static final String CHORUS_OF_SONG_KEY = "chorus";

    private static FirestoreHelper instance = null;
    private FirebaseFirestore firebaseFirestore;

    public static FirestoreHelper getInstance() {
        if (instance == null) {
            instance = new FirestoreHelper();
        }
        return instance;
    }

    private FirestoreHelper() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void migrateAllSongsFromFirestore(SongsRepository songsRepository) {
        firebaseFirestore.collection(SONGS_COLLECTION)
                .orderBy(NUMBER_OF_SONG_KEY)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        List<Song> songs = mapToSongs(documents);
                        songsRepository.addAll(songs);
                    }
                });
    }

    private List<Song> mapToSongs(@NotNull List<DocumentSnapshot> documents) {
        ArrayList<Song> songs = new ArrayList<>();

        for (DocumentSnapshot document : documents) {
            int songNumber = (int) document.get(NUMBER_OF_SONG_KEY);
            String songName = (String) document.get(NAME_OF_SONG_KEY);
            String songText = (String) document.get(TEXT_OF_SONG_KEY);
            String songChorus = (String) document.get(CHORUS_OF_SONG_KEY);

            Song song = new Song(songNumber, songName, songText, songChorus);
            songs.add(song);
        }

        return songs;
    }

    public void updateIfNeeded(int latestSongNumber, SongsRepository songsRepository) {
        firebaseFirestore.collection(SONGS_COLLECTION)
                .orderBy(NUMBER_OF_SONG_KEY)
                .whereGreaterThan(NUMBER_OF_SONG_KEY, latestSongNumber)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        List<Song> songs = mapToSongs(documents);
                        songsRepository.addAll(songs);
                    }
                });
    }
}
