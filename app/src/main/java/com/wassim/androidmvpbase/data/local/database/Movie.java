package com.wassim.androidmvpbase.data.local.database;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;
import com.wassim.androidmvpbase.MovieModel;

/**
 * Created by wassim on 9/6/16.
 */

@AutoValue
public abstract class Movie implements MovieModel, Parcelable {

    static final Factory<Movie> FACTORY = new Factory<>(AutoValue_Movie::new);

    static final RowMapper<Movie> MAPPER_ALL = FACTORY.select_allMapper();
    static final RowMapper<Movie> MAPPER_BY_ID = FACTORY.select_by_idMapper();
    static final RowMapper<Movie> TITLE_MAPPER = FACTORY.select_by_titleMapper();
    static final RowMapper<Movie> YEAR_MAPPER = FACTORY.select_by_release_yearMapper();
}