package com.wassim.androidmvpbase.injection.module;

import android.app.Application;
import android.content.Context;

import com.wassim.androidmvpbase.data.local.preferences.PreferencesHelper;
import com.wassim.androidmvpbase.data.remote.ApiService;
import com.wassim.androidmvpbase.data.remote.OkHttpHelper;
import com.wassim.androidmvpbase.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
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

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return OkHttpHelper.Factory.getClient();
    }

    @Provides
    @Singleton
    ApiService provideApiService(OkHttpClient client) {
        return ApiService.Factory.create(client);
    }

    @Provides
    @Singleton
    PreferencesHelper providesPreferencesHelper(@ApplicationContext Context context){
        return new PreferencesHelper(context);
    }

}
