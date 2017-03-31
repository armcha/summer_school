package com.luseen.yandexsummerschool.data.api;

import com.luseen.yandexsummerschool.base_mvp.api.ResultListener;
import com.luseen.yandexsummerschool.model.YaError;
import com.luseen.yandexsummerschool.utils.Logger;

import java.io.IOException;

import retrofit2.HttpException;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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

    public <T> void startRequest(final Observable<T> request,
                                 final ResultListener resultListener,
                                 final RequestType requestType) {

        resultListener.onStart(requestType);
        Subscription subscription = request
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(() -> Logger.log(requestType.toString().toUpperCase() +
                        " request finished, Unsubscribing"))
                .subscribe(response -> resultListener.onSuccess(requestType, response),
                        throwable -> {
                            resultListener.onError(requestType, throwable);
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
