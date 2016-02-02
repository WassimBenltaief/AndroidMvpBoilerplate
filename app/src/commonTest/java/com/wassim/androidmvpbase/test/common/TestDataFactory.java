package com.wassim.androidmvpbase.test.common;

import com.google.gson.Gson;
import com.wassim.androidmvpbase.data.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
public class TestDataFactory {

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static List<Movie> makeMovies(){

        String jsonMovie = "{\n" +
                "\"id\": 1,\n" +
                "\"title\": \"Dawn of the Planet of the Apes\",\n" +
                "\"image\": \"https://lh3.googleusercontent.com/-zzPZ3rv8IQs/Vq4Krg8TEDI/AAAAAAAAAp8/efWYKQxO_vQ/s800-Ic42/1.jpg\",\n" +
                "\"rating\": 8.3,\n" +
                "\"releaseYear\": 2014,\n" +
                "\"genre\": \"Action\",\n" +
                "\"synopsis\": \"A growing nation of genetically evolved apes led by Caesar is threatened by a band of human survivors of the devastating virus unleashed a decade earlier.\"\n" +
                "}";

        Movie movie = new Gson().fromJson(jsonMovie, Movie.class);
        List<Movie> mMovies = new ArrayList<>();
        mMovies.add(movie);
        return mMovies;
    }


}