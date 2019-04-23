package com.nlapin.youthsongs.network;

import com.nlapin.youthsongs.models.UnsplashPhoto;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String BASE_URL = "https://api.unsplash.com";
    public static final String ACCESS_KEY = "4a879792706515e4b2b7126732a400d84c897f49cde17a2f79755339eb8c1665";

    private Retrofit retrofit;

    public NetworkService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public JSONPlaceHolderAPI getJsonApi() {
        return retrofit.create(JSONPlaceHolderAPI.class);
    }

    public void getSongCover(Callback<UnsplashPhoto> unsplashPhotoCallback) {
        getJsonApi().getRandomPost(BASE_URL, "textures")
                .enqueue(unsplashPhotoCallback);
    }
}
