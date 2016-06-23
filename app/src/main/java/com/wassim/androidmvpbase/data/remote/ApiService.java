package com.wassim.androidmvpbase.data.remote;

import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.util.Vars;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

public interface ApiService {

    @GET("movies.json")
    Observable<List<Movie>> getMovies();

    @POST("movies")
    Observable<Movie> saveMovie();

    @PUT("movies")
    Observable<Movie> updateMovie();

    @DELETE("movies")
    Observable<Movie> deleteMovie();

    class Factory {
        public static ApiService create(OkHttpClient client) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Vars.ENDPOINT)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(ApiService.class);
        }
    }
}
