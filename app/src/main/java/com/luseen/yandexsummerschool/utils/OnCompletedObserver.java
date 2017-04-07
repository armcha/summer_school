package com.luseen.yandexsummerschool.utils;

import rx.Observer;

/**
 * Created by Chatikyan on 07.04.2017.
 */

public abstract class OnCompletedObserver<T> implements Observer<T> {

    @Override
    public void onNext(T o) {
        //no-op
    }

    @Override
    public void onError(Throwable e) {
        //no-op
    }
}