package com.nlapin.youthsongs.data.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nlapin.youthsongs.models.PixelsResponseModel;
import com.nlapin.youthsongs.models.Song;
import com.nlapin.youthsongs.network.NetworkService;
import com.nlapin.youthsongs.utils.SongUtils;

import java.io.FileNotFoundException;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Maybe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
    public static final String COVER_URI_LARGE = "cover_uri_large";
    public static final String COVER_URI_SMALL = "cover_uri_small";

    private FirebaseFirestore firebaseFirestore;

    public SongCloudRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public Flowable<List<Song>> provideAllSongs() {
        return Flowable.create((FlowableEmitter<List<Song>> emitter) ->
                firebaseFirestore.collection(SONGS_COLLECTION)
                        .orderBy(NUMBER_OF_SONG_KEY)
                        .get()
                        .addOnCompleteListener(task -> {
                            QuerySnapshot result = task.getResult();
                            if (result != null && !result.isEmpty()) {
                                List<DocumentSnapshot> documents = result.getDocuments();
                                List<Song> songs = SongUtils.mapToSongs(documents);
                                emitter.onNext(songs);
                            } else {
                                emitter.onNext(null);
                            }
                            emitter.onComplete();
                        })
                        .addOnFailureListener(emitter::onError), BackpressureStrategy.BUFFER);
    }

    public Maybe<Song> provideSongById(int id) {
        return Maybe.create((MaybeOnSubscribe<Song>) emitter -> {
            firebaseFirestore.collection(SONGS_COLLECTION)
                    .whereEqualTo(NUMBER_OF_SONG_KEY, id)
                    .get()
                    .addOnCompleteListener(task -> {
                        QuerySnapshot result = task.getResult();
                        if (result != null && !result.isEmpty()) {
                            List<DocumentSnapshot> documents = result.getDocuments();
                            List<Song> songs = SongUtils.mapToSongs(documents);
                            Song song = songs.get(0);

                            getSongCover(song).subscribe(song::setCoverUrlSmall).dispose();
                            emitter.onSuccess(song);
                        } else {
                            emitter.onError(new FileNotFoundException("Song by this id not found"));
                        }
                    }).addOnFailureListener(emitter::onError);

        });
    }

    private Single<String> getSongCover(Song song) {
        return Single.create(emitter -> {
            String coverUrlSmall = song.getCoverUrlSmall();
            if (coverUrlSmall != null) {
                emitter.onSuccess(coverUrlSmall);
            } else {
                new NetworkService().getSongCover(new Callback<PixelsResponseModel>() {
                    @Override
                    public void onResponse(@NonNull Call<PixelsResponseModel> call,
                                           @NonNull Response<PixelsResponseModel> response) {
                        PixelsResponseModel pixelsResponseModel = response.body();
                        if (pixelsResponseModel != null) {
                            emitter.onSuccess(pixelsResponseModel.getPhotos().getSrc().getOriginal());
                        } else {
                            Log.e(TAG, "Receiving song cover was failed! - " + response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PixelsResponseModel> call,
                                          @NonNull Throwable t) {
                        emitter.onError(t);
                    }
                });
            }
        });
    }

    public void deleteSong(Song song) {
        Completable.fromAction(() -> {
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
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void updateSong(Song song) {
        Completable.fromAction(() ->
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
                            documentReference.update(COVER_URI_SMALL, song.getCoverUrlSmall());
                        })).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
