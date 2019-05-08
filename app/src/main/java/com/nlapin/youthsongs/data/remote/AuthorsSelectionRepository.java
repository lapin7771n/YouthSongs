package com.nlapin.youthsongs.data.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class AuthorsSelectionRepository {

    private static final String AUTHORS_SELECTION_COLLECTION = "authorsSelection";
    private static final String SONG_NUMBER_FIELD = "songNumber";
    private static final String VIEWS_FIELD = "views";


    public void addViewOnSong(int songNumber) {
        Completable.fromAction(() -> {
            final Task<QuerySnapshot> querySnapshotTask = FirebaseFirestore.getInstance().collection(AUTHORS_SELECTION_COLLECTION)
                    .whereEqualTo(SONG_NUMBER_FIELD, songNumber)
                    .get();

            querySnapshotTask
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        final DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        Integer songViews = (Integer) documentSnapshot.get(VIEWS_FIELD);
                        
                    });
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }
    
    private void setViewsToSong(DocumentSnapshot documentSnapshot, int views){
        Completable.fromAction(()->{

        }).subscribeOn(Schedulers.io())
                .subscribe();
    }
}
