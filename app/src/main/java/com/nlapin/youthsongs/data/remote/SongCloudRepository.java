package com.nlapin.youthsongs.data.remote;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.models.Song;
import com.nlapin.youthsongs.models.UnsplashPhoto;
import com.nlapin.youthsongs.network.NetworkService;
import com.nlapin.youthsongs.utils.SongUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public static final String COVER_URI = "cover_uri";

    private FirebaseFirestore firebaseFirestore;

    public SongCloudRepository(Context context) {
        firebaseFirestore = FirebaseFirestore.getInstance();
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
                        Song song = songs.get(0);

                        provideSongCoverAndSend(callback, song);
                    } else {
                        callback.onCallback(null);
                    }
                })
                .addOnFailureListener(e -> callback.onCallback(null));
    }

    private void provideSongCoverAndSend(ProvideSongCallback callback, Song song) {
        if (song.getCoverUrl() == null) {
            FirebaseStorageHelper firestorageHelper = YouthSongsApp.getComponent()
                    .getFirestorageHelper();

            firestorageHelper.downloadSongCover(song.getId(), uri -> {
                if (uri == null || uri.toString().isEmpty()) {
                    new NetworkService().getSongCover(new Callback<UnsplashPhoto>() {
                        @Override
                        public void onResponse(Call<UnsplashPhoto> call, Response<UnsplashPhoto> response) {
                            UnsplashPhoto unsplashPhoto = response.body();
                            song.setCoverUrl(unsplashPhoto.getUrls().getRaw());
                        }

                        @Override
                        public void onFailure(Call<UnsplashPhoto> call, Throwable t) {

                        }
                    });
                } else {
                    song.setCoverUrl(uri.toString());
                    callback.onCallback(song);
                }
            });
        } else {
            callback.onCallback(song);
        }
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
        firebaseFirestore.collection(SONGS_COLLECTION)
                .whereEqualTo(NUMBER_OF_SONG_KEY, song.getId())
                .whereEqualTo(NAME_OF_SONG_KEY, song.getName())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    DocumentReference documentReference = queryDocumentSnapshots.getDocuments()
                            .get(0)
                            .getReference();

                    documentReference.update(NUMBER_OF_SONG_KEY, song.getId());
                    documentReference.update(NAME_OF_SONG_KEY, song.getName());
                    documentReference.update(TEXT_OF_SONG_KEY, song.getText());
                    documentReference.update(CHORUS_OF_SONG_KEY, song.getChorus());
                    documentReference.update(COVER_URI, song.getCoverUrl());
                });
    }

    public interface AllSongsCallback {
        void onCallback(List<Song> songs);
    }

    public interface ProvideSongCallback {
        void onCallback(Song song);
    }
}
