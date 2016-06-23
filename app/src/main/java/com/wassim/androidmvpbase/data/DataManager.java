package com.wassim.androidmvpbase.data;

import com.wassim.androidmvpbase.data.local.database.DatabaseHelper;
import com.wassim.androidmvpbase.data.local.preferences.PreferencesHelper;
import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.data.remote.ApiService;
import com.wassim.androidmvpbase.util.RxEventBusHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private final ApiService mApiService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final RxEventBusHelper mEventPoster;

    @Inject
    public DataManager(ApiService apiService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper, RxEventBusHelper rxEventBusHelper) {
        mApiService = apiService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
        mEventPoster = rxEventBusHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public ApiService getApiService() {
        return mApiService;
    }

    public DatabaseHelper getDatabaseHelper() {
        return mDatabaseHelper;
    }

    public RxEventBusHelper getEventPoster() {
        return mEventPoster;
    }

    public void removeMovie(Movie movie){
        mDatabaseHelper.removeMovie(movie);
    }

    public void addMovie(Movie movie){
        mDatabaseHelper.addMovie(movie);
    }

}
