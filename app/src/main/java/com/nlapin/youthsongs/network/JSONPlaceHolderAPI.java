package com.nlapin.youthsongs.network;

import com.nlapin.youthsongs.models.PixelsResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderAPI {
    @GET("/v1/search/")
    Call<PixelsResponseModel> getRandomPhoto(@Query("query") String query,
                                             @Query("per_page") String per_page,
                                             @Query("page") String page);
}
