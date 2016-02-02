package com.wassim.androidmvpbase.test.common.injection.component;

import com.wassim.androidmvpbase.injection.component.ApplicationComponent;
import com.wassim.androidmvpbase.test.common.injection.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
