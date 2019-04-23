package com.nlapin.youthsongs.data.remote;

import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class FirebaseStorageHelper {

    private static final String PATH = "gs://youth-songs-development.appspot.com";
    public static final String COVERS = "covers/";

    private static FirebaseStorage FIREBASE_STORAGE;

    public FirebaseStorageHelper() {
        FIREBASE_STORAGE = FirebaseStorage.getInstance(PATH);
    }

    public void uploadData(String fileName, byte[] bytes,
                           OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener) {
        UploadTask uploadTask = FIREBASE_STORAGE
                .getReference(COVERS)
                .child(fileName)
                .putBytes(bytes);

        if (onSuccessListener != null) {
            uploadTask.addOnSuccessListener(onSuccessListener);
        }
    }

    public void downloadSongCover(int songId, OnSuccessListener<Uri> onSuccessListener) {
        FIREBASE_STORAGE
                .getReference(COVERS)
                .child(String.valueOf(songId))
                .getDownloadUrl()
                .addOnSuccessListener(onSuccessListener);
    }
}
