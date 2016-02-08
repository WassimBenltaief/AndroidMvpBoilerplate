package com.wassim.androidmvpbase.injection.component;

import android.app.Application;
import android.content.Context;

import com.wassim.androidmvpbase.data.DataManager;
import com.wassim.androidmvpbase.data.SyncService;
import com.wassim.androidmvpbase.data.local.database.DatabaseHelper;
import com.wassim.androidmvpbase.data.local.preferences.PreferencesHelper;
import com.wassim.androidmvpbase.data.remote.ApiService;
import com.wassim.androidmvpbase.injection.ApplicationContext;
import com.wassim.androidmvpbase.injection.module.ApplicationModule;
import com.squareup.otto.Bus;
import com.wassim.androidmvpbase.util.RxEventBusHelper;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    ApiService apiService();

    DataManager datamanager();

}
