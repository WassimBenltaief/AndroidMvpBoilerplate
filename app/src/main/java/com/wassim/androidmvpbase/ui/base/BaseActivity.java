package com.wassim.androidmvpbase.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wassim.androidmvpbase.App;
import com.wassim.androidmvpbase.injection.component.ActivityComponent;
import com.wassim.androidmvpbase.injection.component.DaggerActivityComponent;
import com.wassim.androidmvpbase.injection.module.ActivityModule;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(App.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

}
