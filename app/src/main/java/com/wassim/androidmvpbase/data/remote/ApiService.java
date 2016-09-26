package com.wassim.androidmvpbase.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wassim.androidmvpbase.data.model.RemoteMovie;
import com.wassim.androidmvpbase.util.Vars;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

public interface ApiService {

    @GET("movies.json")
    Observable<List<RemoteMovie>> getMovies();

    @POST("movies")
    Observable<RemoteMovie> saveMovie();

    @PUT("movies")
    Observable<RemoteMovie> updateMovie();

    @DELETE("movies")
    Observable<RemoteMovie> deleteMovie();

    class Factory {
        public static ApiService create(OkHttpClient client) {

            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Vars.ENDPOINT)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(ApiService.class);
        }
    }
}
