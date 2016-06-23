package com.wassim.androidmvpbase;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

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
        // enable strict mode in debug only
        if(BuildConfig.DEBUG){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
            );
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
            );
        }
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
