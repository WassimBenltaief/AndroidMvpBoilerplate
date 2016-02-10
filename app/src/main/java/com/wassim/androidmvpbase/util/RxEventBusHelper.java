package com.wassim.androidmvpbase.util;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Provides helper methods to post event to an Otto event bus
 */
@Singleton
public class RxEventBusHelper {

    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    @Inject
    public RxEventBusHelper() {
    }

    /**
     * Pass any event down to event listeners.
     */
    public void send(final Object object) {
       _bus. onNext(object);
    }

    /**
     * Subscribe to this Observable. On event, do something
     * e.g. replace a fragment
     */
    public Observable<Object> toObserverable() {
        return _bus;
    }

    public boolean hasObservers(){
        return _bus.hasObservers();
    }
}
