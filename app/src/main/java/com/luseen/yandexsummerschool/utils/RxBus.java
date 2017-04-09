package com.luseen.yandexsummerschool.utils;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Chatikyan on 01.03.2017.
 */

public class RxBus<T> {

    private final BehaviorSubject<T> bus = BehaviorSubject.create();

    public void send(T o) {
        bus.onNext(o);
    }

    public Observable<T> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}

