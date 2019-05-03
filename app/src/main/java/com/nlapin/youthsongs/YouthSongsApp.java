package com.nlapin.youthsongs;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.nlapin.youthsongs.data.remote.SongCloudRepository;
import com.nlapin.youthsongs.di.ApplicationModule;
import com.nlapin.youthsongs.di.DaggerMainComponent;
import com.nlapin.youthsongs.di.MainComponent;
import com.nlapin.youthsongs.models.PixelsResponseModel;
import com.nlapin.youthsongs.models.Song;
import com.nlapin.youthsongs.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YouthSongsApp extends Application {

    private static final String TAG = "YouthSongsApp";
    private static MainComponent mainComponent;

    public static MainComponent getComponent() {
        return mainComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        // TODO: 3/22/2019 uncomment on production
        //Fabric.with(this, new Crashlytics());

        mainComponent = buildComponent();

        provideImages();
    }

    private void provideImages() {
        SongCloudRepository songCloudRepository = mainComponent.getSongCloudRepository();
        NetworkService networkService = mainComponent.getNetworkService();
        songCloudRepository.provideAllSongs(songs -> {
            Log.i(TAG, "All song provided from server!");
            for (Song song : songs) {

                if (song.getCoverUrlLarge() != null && song.getCoverUrlSmall() != null) {
                    Log.d(TAG, "Image provided!");
                    continue;
                }

                networkService.getSongCover(new Callback<PixelsResponseModel>() {
                    @Override
                    public void onResponse(Call<PixelsResponseModel> call, Response<PixelsResponseModel> response) {
                        Log.i(TAG, "Response - " + response.raw());
                        PixelsResponseModel pixelsResponseModel = response.body();
                        if (pixelsResponseModel != null) {
                            String original = pixelsResponseModel.getPhotos().getSrc().getOriginal();
                            String small = pixelsResponseModel.getPhotos().getSrc().getSmall();
                            song.setCoverUrlLarge(original);
                            song.setCoverUrlSmall(small);
                            songCloudRepository.updateSong(song);
                            Log.i(TAG, "Song by id - " + song.getId() + " - updated with covers: "
                                    + original + "\n" + small);
                        } else {
                            Log.e(TAG, "Response body is NULL!");
                        }
                    }

                    @Override
                    public void onFailure(Call<PixelsResponseModel> call, Throwable t) {
                        Log.e(TAG, "Failed to load songs cover from server!", t);
                    }
                });
            }
        });
    }

    protected MainComponent buildComponent() {
        return DaggerMainComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
