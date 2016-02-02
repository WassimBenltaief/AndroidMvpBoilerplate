package com.wassim.androidmvpbase.injection.module;

import android.app.Application;
import android.content.Context;

import com.wassim.androidmvpbase.data.remote.ApiService;
import com.wassim.androidmvpbase.injection.ApplicationContext;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    Bus provideEventBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    ApiService provideApiService() {
        return ApiService.Factory.create();
    }

}
