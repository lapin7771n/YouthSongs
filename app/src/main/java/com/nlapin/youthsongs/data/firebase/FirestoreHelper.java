package com.nlapin.youthsongs.data.firebase;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nlapin.youthsongs.data.SongDao;
import com.nlapin.youthsongs.models.Song;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Class helper for working with FireStorage
 */
public class FirestoreHelper {

    private static final String TAG = "FirestoreHelper";

    /**
     * Key for song collection in FireSorage
     * root package
     */
    private static final String SONGS_COLLECTION = "songs";

    /**
     * Fields in song collections
     */
    private static final String NAME_OF_SONG_KEY = "name";
    private static final String NUMBER_OF_SONG_KEY = "number";
    private static final String TEXT_OF_SONG_KEY = "text";
    private static final String CHORUS_OF_SONG_KEY = "chorus";

    /**
     * FireStore API object
     */
    private FirebaseFirestore firebaseFirestore;

    @Inject
    public FirestoreHelper() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    /**
     * This method migrate all songs from Song collection in FireStorage
     *
     * @param songDao    Local database
     * @param uiCallback callback for updating UI
     */
    public void migrateAllSongsFromFirestore(SongDao songDao, UICallback uiCallback) {
        Log.d(TAG, "Migrating song from Firestore");
        firebaseFirestore.collection(SONGS_COLLECTION)
                .orderBy(NUMBER_OF_SONG_KEY)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        List<Song> songs = mapToSongs(documents);

                        new Thread(() -> {
                            songDao.insertAll(songs.toArray(new Song[0]));
                            Log.d(TAG, "Songs migrated in background thread.");
                        }).start();

                        if (uiCallback != null)
                            uiCallback.renderUI(songs);
                        Log.d(TAG, "All songs migrated! Count - " + songs.size());
                    } else {
                        Log.d(TAG, "Can't load data from firestore");
                        Log.d(TAG, "Response - " + task);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "Error: " + e);
                });
    }

    /**
     * Helper method to map Json document to SongObject
     *
     * @param documents json object
     * @return list of recieved songs
     */
    private List<Song> mapToSongs(List<DocumentSnapshot> documents) {
        ArrayList<Song> songs = new ArrayList<>();

        //Going through all received songs
        for (DocumentSnapshot document : documents) {
            long songNumber = (long) document.get(NUMBER_OF_SONG_KEY);
            String songName = (String) document.get(NAME_OF_SONG_KEY);
            String songText = (String) document.get(TEXT_OF_SONG_KEY);
            String songChorus = (String) document.get(CHORUS_OF_SONG_KEY);

            Song song = new Song((int) songNumber, songName, songText, songChorus);
            songs.add(song);
        }

        return songs;
    }

    /**
     * Uploading new songs from remote database
     *
     * @param latestSongNumber last local song number
     * @param songDao          local database API
     * @param uiCallback       callback for updating UI
     */
    public void updateIfNeeded(int latestSongNumber, SongDao songDao, UICallback uiCallback) {
        Log.d(TAG, "Updating songs from Firestore");
        firebaseFirestore.collection(SONGS_COLLECTION)
                .orderBy(NUMBER_OF_SONG_KEY)
                .whereGreaterThan(NUMBER_OF_SONG_KEY, latestSongNumber)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        List<Song> songs = mapToSongs(documents);
                        songDao.insertAll(songs.toArray(new Song[songs.size()]));
                        uiCallback.renderUI(songs);
                        Log.d(TAG, "Song Updated! Count - " + songs.size());
                    }
                });
    }
}
