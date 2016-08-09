package com.wassim.androidmvpbase.data.model;


import android.support.annotation.NonNull;

import com.squareup.sqldelight.RowMapper;
import com.wassim.androidmvpbase.MovieModel;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(allParameters = true)
public abstract class Movie implements MovieModel {

    public static final Factory<Movie> FACTORY = new Factory<>(new Creator<Movie>() {
        @Override
        public Movie create(long id, @NonNull String title, @NonNull String image,
                                 double rating, long releaseYear, @NonNull String genre,
                                 @NonNull String synopsis) {
            return ImmutableMovie.of(id, title, image, rating, releaseYear, genre, synopsis);
        }
    });


    public static final RowMapper<Movie> MAPPER_ALL = FACTORY.select_allMapper();
    public static final RowMapper<Movie> MAPPER_BY_ID = FACTORY.select_by_idMapper();
    public static final RowMapper<Movie> TITLE_MAPPER = FACTORY.select_by_titleMapper();
    public static final RowMapper<Movie> YEAR_MAPPER = FACTORY.select_by_release_yearMapper();
}
