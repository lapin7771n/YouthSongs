package com.nlapin.youthsongs.network;

import com.nlapin.youthsongs.models.UnsplashPhoto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderAPI {
    @GET("/photos/random/")
    public Call<UnsplashPhoto> getRandomPost(@Query("client_id") String clientId,
                                             @Query("query") String query);
}
