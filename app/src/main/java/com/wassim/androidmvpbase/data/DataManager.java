package com.wassim.androidmvpbase.data;

import com.wassim.androidmvpbase.data.local.database.DatabaseHelper;
import com.wassim.androidmvpbase.data.local.preferences.PreferencesHelper;
import com.wassim.androidmvpbase.data.remote.ApiService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private final ApiService mApiService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(ApiService apiService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mApiService = apiService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
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

}
