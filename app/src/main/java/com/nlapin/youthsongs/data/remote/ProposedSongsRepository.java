package com.nlapin.youthsongs.data.remote;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class ProposedSongsRepository {

    public static ProposedSongsRepository instance = null;

    private static final String TAG = "ProposedSongsRepository";

    private static final String PROPOSED_SONGS_COLLECTION = "proposedSongs";
    private static final String NAME_FIELD = "name";
    private static final String LINK_FIELD = "link";

    private ProposedSongsRepository() {
    }

    public static ProposedSongsRepository getInstance() {
        if (instance == null)
            instance = new ProposedSongsRepository();
        return instance;
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
