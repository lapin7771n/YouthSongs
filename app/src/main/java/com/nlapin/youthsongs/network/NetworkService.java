package com.nlapin.youthsongs.network;

import com.nlapin.youthsongs.models.UnsplashPhoto;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String BASE_URL = "https://api.unsplash.com";
    //    public static final String ACCESS_KEY = "4a879792706515e4b2b7126732a400d84c897f49cde17a2f79755339eb8c1665";
    private static final String API_KEY = "563492ad6f917000010000010bc78013a83a490980bdada2619f87f1";

    private Retrofit retrofit;

    public NetworkService() {
        final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        final Interceptor interceptor = chain -> {
            Request request = chain.request();
            final Headers headers = request.headers().newBuilder().add("Authorization", API_KEY).build();
            request = request.newBuilder().headers(headers).build();
            return chain.proceed(request);
        };

        clientBuilder.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public JSONPlaceHolderAPI getJsonApi() {
        return retrofit.create(JSONPlaceHolderAPI.class);
    }

    public void getSongCover(Callback<UnsplashPhoto> unsplashPhotoCallback) {
        getJsonApi().getRandomPhoto(ACCESS_KEY, "textures")
                .enqueue(unsplashPhotoCallback);
    }
}
