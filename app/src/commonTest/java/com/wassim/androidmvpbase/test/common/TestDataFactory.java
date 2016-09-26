package com.wassim.androidmvpbase.test.common;

import com.google.gson.Gson;
import com.wassim.androidmvpbase.data.model.RemoteMovie;

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

    public static List<RemoteMovie> makeMovies(){

        String jsonMovie = "{\n" +
                "\"id\": 1,\n" +
                "\"title\": \"Dawn of the Planet of the Apes\",\n" +
                "\"image\": \"https://lh3.googleusercontent.com/-zzPZ3rv8IQs/Vq4Krg8TEDI/AAAAAAAAAp8/efWYKQxO_vQ/s800-Ic42/1.jpg\",\n" +
                "\"rating\": 8.3,\n" +
                "\"releaseYear\": 2014,\n" +
                "\"genre\": \"Action\",\n" +
                "\"synopsis\": \"A growing nation of genetically evolved apes led by Caesar is threatened by a band of human survivors of the devastating virus unleashed a decade earlier.\"\n" +
                "}";

        String jsonMovie2 = "{\n" +
                "\"id\": 2,\n" +
                "\"title\": \"District 9\",\n" +
                "\"image\": \"https://lh3.googleusercontent.com/-K8J4efkHoQM/Vq4KsRTG1yI/AAAAAAAAAqU/UesoWr5cSu8/s800-Ic42/2.jpg\",\n" +
                "\"rating\": 8,\n" +
                "\"releaseYear\": 2009,\n" +
                "\"genre\": \"Sci-Fi\",\n" +
                "\"synopsis\": \"An extraterrestrial race forced to live in slum-like conditions on Earth suddenly finds a kindred spirit in a government agent who is exposed to their biotechnology.\"\n" +
                "}";


        RemoteMovie movie = new Gson().fromJson(jsonMovie, RemoteMovie.class);
        RemoteMovie movie2 = new Gson().fromJson(jsonMovie2, RemoteMovie.class);
        List<RemoteMovie> mMovies = new ArrayList<>();
        mMovies.add(movie);
        mMovies.add(movie2);
        return mMovies;
    }

    public static RemoteMovie makeMovie(){

        String jsonMovie = "{\n" +
                "\"id\": 1,\n" +
                "\"title\": \"Dawn of the Planet of the Apes\",\n" +
                "\"image\": \"https://lh3.googleusercontent.com/-zzPZ3rv8IQs/Vq4Krg8TEDI/AAAAAAAAAp8/efWYKQxO_vQ/s800-Ic42/1.jpg\",\n" +
                "\"rating\": 8.3,\n" +
                "\"releaseYear\": 2014,\n" +
                "\"genre\": \"Action\",\n" +
                "\"synopsis\": \"A growing nation of genetically evolved apes led by Caesar is threatened by a band of human survivors of the devastating virus unleashed a decade earlier.\"\n" +
                "}";
        return new Gson().fromJson(jsonMovie, RemoteMovie.class);
    }


}