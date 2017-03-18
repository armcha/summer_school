package com.luseen.yandexsummerschool.data;

import android.util.Log;

import com.luseen.yandexsummerschool.base_mvp.api.ResultListener;
import com.luseen.yandexsummerschool.model.Translation;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Chatikyan on 18.03.2017.
 */

public class ApiCallMaker {

    private CompositeSubscription subscriptions;

    public ApiCallMaker() {
        subscriptions = new CompositeSubscription();
    }

    public <T> void startRequest(final Observable<T> query,
                                 final ResultListener resultListener) {
        Subscription subscription = query
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        Log.e("onCompleted ", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError ", "onError " + e.getMessage());
                        Log.e("onError ", "onError " + e);
                    }

                    @Override
                    public void onNext(T ts) {
                        Translation translation = ((Translation) ts);
                        Log.e("onNext ", "onNext " + translation.getText().toString());
                    }
                });
        subscriptions.add(subscription);
    }

    public void release() {
        if (subscriptions != null &&
                subscriptions.hasSubscriptions() &&
                !subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }
}
