package com.nlapin.youthsongs.data.remote;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class AuthorsSelectionRepository {

    private static final String AUTHORS_SELECTION_COLLECTION = "authorsSelection";
    private static final String SONG_NUMBER_FIELD = "songNumber";
    private static final String VIEWS_FIELD = "views";
    private static final String FAV_COUNT_FIELD = "fav_count";

    public static final int FIRST_VIEW = 1;
    private CollectionReference authorsSelectionCollection;

    public AuthorsSelectionRepository() {
        authorsSelectionCollection = FirebaseFirestore.getInstance().collection(AUTHORS_SELECTION_COLLECTION);
    }

    public void addViewOnSong(int songNumber) {
        Completable.fromAction(() -> {
            authorsSelectionCollection
                    .whereEqualTo(SONG_NUMBER_FIELD, songNumber)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        final List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        if (documents.isEmpty()) {
                            final HashMap<String, Integer> songViews = new HashMap<>();
                            songViews.put(SONG_NUMBER_FIELD, songNumber);
                            songViews.put(VIEWS_FIELD, FIRST_VIEW);
                            authorsSelectionCollection.add(songViews);
                        } else {
                            final DocumentSnapshot documentSnapshot = documents.get(0);
                            long songViews = (long) documentSnapshot.get(VIEWS_FIELD) + 1;
                            setViewsToSong(documentSnapshot, songViews);
                        }
                    });
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void addFavOnSong(int songNumber) {
        Completable.fromAction(() -> {
            authorsSelectionCollection
                    .whereEqualTo(SONG_NUMBER_FIELD, songNumber)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        final List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        if (documents.isEmpty()) {
                            final HashMap<String, Integer> songFavorites = new HashMap<>();
                            songFavorites.put(SONG_NUMBER_FIELD, songNumber);
                            songFavorites.put(FAV_COUNT_FIELD, 1);
                            authorsSelectionCollection.add(songFavorites);
                        } else {
                            final DocumentSnapshot documentSnapshot = documents.get(0);
                            final Object currentFavorites = documentSnapshot.get(FAV_COUNT_FIELD);
                            long favCount;

                            if (currentFavorites == null) {
                                favCount = 1;
                            } else {
                                favCount = (long) currentFavorites + 1;
                            }

                            setFavCountToSong(documentSnapshot, favCount);
                        }
                    });
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }

    private void setViewsToSong(final DocumentSnapshot documentSnapshot, final long views) {
        FirebaseFirestore.getInstance()
                .runTransaction((Transaction.Function<Void>) transaction -> {
                    transaction.update(documentSnapshot.getReference(), VIEWS_FIELD, views);
                    return null;
                });
    }

    private void setFavCountToSong(final DocumentSnapshot documentSnapshot, final long favCount) {
        FirebaseFirestore.getInstance()
                .runTransaction((Transaction.Function<Void>) transaction -> {
                    transaction.update(documentSnapshot.getReference(), FAV_COUNT_FIELD, favCount);
                    return null;
                });
    }
}
