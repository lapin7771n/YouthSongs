package com.nlapin.youthsongs.data.remote;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nlapin.youthsongs.models.Song;
import com.nlapin.youthsongs.utils.SongUtils;

import java.util.List;

public class SongCloudRepository {

    private static final String TAG = "SongCloudRepository";

    /**
     * Key for song collection in FireSorage
     * root package
     */
    private static final String SONGS_COLLECTION = "songs";

    /**
     * Fields in song collections
     */
    public static final String NAME_OF_SONG_KEY = "name";
    public static final String NUMBER_OF_SONG_KEY = "number";
    public static final String TEXT_OF_SONG_KEY = "text";
    public static final String CHORUS_OF_SONG_KEY = "chorus";

    private FirebaseFirestore firebaseFirestore;
    private Context context;

    public SongCloudRepository(Context context) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.context = context;
    }

    public void provideAllSongs(AllSongsCallback callback) {
        firebaseFirestore.collection(SONGS_COLLECTION)
                .orderBy(NUMBER_OF_SONG_KEY)
                .get()
                .addOnCompleteListener(task -> {
                    QuerySnapshot result = task.getResult();
                    if (result != null && !result.isEmpty()) {
                        List<DocumentSnapshot> documents = result.getDocuments();
                        List<Song> songs = SongUtils.mapToSongs(documents);
                        callback.onCallback(songs);
                    } else {
                        callback.onCallback(null);
                    }
                })
                .addOnFailureListener(e -> {
                    callback.onCallback(null);
                });
    }

    public void provideSongById(int id, ProvideSongCallback callback) {
        firebaseFirestore.collection(SONGS_COLLECTION)
                .whereEqualTo(NUMBER_OF_SONG_KEY, id)
                .get()
                .addOnCompleteListener(task -> {
                    QuerySnapshot result = task.getResult();
                    if (result != null && !result.isEmpty()) {
                        List<DocumentSnapshot> documents = result.getDocuments();
                        List<Song> songs = SongUtils.mapToSongs(documents);
                        callback.onCallback(songs.get(0));
                    } else {
                        callback.onCallback(null);
                    }
                })
                .addOnFailureListener(e -> callback.onCallback(null));
    }

    public void deleteSong(Song song) {
        Task<QuerySnapshot> querySnapshotTask = firebaseFirestore.collection(SONGS_COLLECTION)
                .whereEqualTo(NAME_OF_SONG_KEY, song.getName())
                .whereEqualTo(NUMBER_OF_SONG_KEY, song.getId())
                .get();

        List<DocumentSnapshot> documents = querySnapshotTask.getResult().getDocuments();
        for (DocumentSnapshot document : documents) {
            document.getReference().delete()
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Song successfully deleted!"))
                    .addOnFailureListener(e -> Log.e(TAG, "Song deleting error!", e));
        }
    }

    public void updateSong(Song song) {
        DocumentReference documentReference = firebaseFirestore.collection(SONGS_COLLECTION)
                .whereEqualTo(NUMBER_OF_SONG_KEY, song.getId())
                .whereEqualTo(NAME_OF_SONG_KEY, song.getName())
                .get()
                .getResult()
                .getDocuments()
                .get(0)
                .getReference();

        documentReference.update(NUMBER_OF_SONG_KEY, song.getId());
        documentReference.update(NAME_OF_SONG_KEY, song.getName());
        documentReference.update(TEXT_OF_SONG_KEY, song.getText());
        documentReference.update(CHORUS_OF_SONG_KEY, song.getChorus());
    }

    public interface AllSongsCallback {
        void onCallback(List<Song> songs);
    }

    public interface ProvideSongCallback {
        void onCallback(Song song);
    }
}
