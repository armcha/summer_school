package com.luseen.yandexsummerschool.data.api;

import com.luseen.yandexsummerschool.base_mvp.api.ResultListener;
import com.luseen.yandexsummerschool.utils.Logger;
import com.luseen.yandexsummerschool.utils.RxUtils;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Chatikyan on 18.03.2017.
 */

public class ApiCallMaker {

    private final CompositeSubscription subscriptions;

    public ApiCallMaker() {
        subscriptions = new CompositeSubscription();
    }

    /**
     * Call maker, that make all api request and return result or error
     * @param request api request
     * @param resultListener listener to send results
     * @param requestType current request type
     * @param <T> request Object type
     */
    public <T> void startRequest(final Observable<T> request,
                                 final ResultListener resultListener,
                                 final RequestType requestType) {

        Subscription subscription = request
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> resultListener.onStart(requestType))
                .doOnUnsubscribe(() -> Logger.d(requestType.toString().toUpperCase() +
                        " request finished, Unsubscribing"))
                .subscribe(response -> resultListener.onSuccess(requestType, response),
                        throwable -> resultListener.onError(requestType, throwable));
        subscriptions.add(subscription);
    }

    public void release() {
        RxUtils.unsubscribe(subscriptions);
    }
}
