package com.wassim.androidmvpbase;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.wassim.androidmvpbase.injection.component.ApplicationComponent;
import com.wassim.androidmvpbase.injection.component.DaggerApplicationComponent;
import com.wassim.androidmvpbase.injection.module.ApplicationModule;


public class App extends Application {
    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
