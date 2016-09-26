package com.wassim.androidmvpbase.test.common.injection.module;

import android.app.Application;
import android.content.Context;

import com.squareup.sqlbrite.BriteDatabase;
import com.wassim.androidmvpbase.data.DataManager;
import com.wassim.androidmvpbase.data.local.preferences.PreferencesHelper;
import com.wassim.androidmvpbase.data.remote.ApiService;
import com.wassim.androidmvpbase.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

import static org.mockito.Mockito.mock;

/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
public class ApplicationTestModule {

    private final Application mApplication;

    public ApplicationTestModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    /************* MOCKS *************/

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return mock(DataManager.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return mock(OkHttpClient.class);
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper() {
        return mock(PreferencesHelper.class);
    }

    @Provides
    @Singleton
    ApiService provideApiService() {
        return mock(ApiService.class);
    }

    @Provides
    @Singleton
    BriteDatabase provideBriteDatabase() {
        return mock(BriteDatabase.class);
    }

}
