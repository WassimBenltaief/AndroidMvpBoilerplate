package com.wassim.androidmvpbase.util;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Provides helper methods to post event to an Otto event bus
 */
public class RxEventBusHelper {

    private static RxEventBusHelper instance;

    private PublishSubject<Object> subject = PublishSubject.create();

    @Inject
    public RxEventBusHelper() {
    }

    /**
     * Pass any event down to event listeners.
     */
    public void post(Object object) {
        subject.onNext(object);
    }

    /**
     * Subscribe to this Observable. On event, do something
     * e.g. replace a fragment
     */
    public Observable<Object> get() {
        return subject;
    }
}
