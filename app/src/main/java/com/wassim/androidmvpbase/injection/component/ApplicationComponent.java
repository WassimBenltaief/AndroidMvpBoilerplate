package com.wassim.androidmvpbase.injection.component;

import android.app.Application;
import android.content.Context;

import com.wassim.androidmvpbase.data.DataManager;
import com.wassim.androidmvpbase.data.local.preferences.PreferencesHelper;
import com.wassim.androidmvpbase.data.remote.ApiService;
import com.wassim.androidmvpbase.injection.ApplicationContext;
import com.wassim.androidmvpbase.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    OkHttpClient okHttpClient();

    ApiService apiService();

    PreferencesHelper preferencesHelper();

    DataManager datamanager();

}
