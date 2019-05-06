package com.nlapin.youthsongs.data.remote;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class ProposedSongsRepository {

    private static final String TAG = "ProposedSongsRepository";

    public static final String PROPOSED_SONGS_COLLECTION = "proposedSongs";
    public static final String NAME_FIELD = "name";
    public static final String LINK_FIELD = "link";

    public ProposedSongsRepository() {
    }


    public void addSongToProposed(String name, String link) {
        Completable.fromAction(() -> {
            HashMap<String, String> data = new HashMap<>();
            data.put(NAME_FIELD, name);
            data.put(LINK_FIELD, link);

            FirebaseFirestore.getInstance()
                    .collection(PROPOSED_SONGS_COLLECTION)
                    .document(name)
                    .set(data)
                    .addOnSuccessListener(aVoid ->
                            Log.d(TAG, "DocumentSnapshot successfully written!"))
                    .addOnFailureListener(e ->
                            Log.e(TAG, "Error writing document", e));
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }

}
