package com.wassim.androidmvpbase.injection.component;

import com.wassim.androidmvpbase.injection.PerActivity;
import com.wassim.androidmvpbase.injection.module.ActivityModule;
import com.wassim.androidmvpbase.ui.views.main.MainActivity;
import com.wassim.androidmvpbase.ui.views.single.SingleMovieActivity;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(SingleMovieActivity singleMovieActivity);

}
