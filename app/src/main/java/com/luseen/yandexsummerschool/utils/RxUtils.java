package com.luseen.yandexsummerschool.utils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Chatikyan on 02.04.2017.
 */

public class RxUtils {

    private RxUtils() {
        throw new RuntimeException("Private constructor cannot be accessed");
    }

    //Helper method to unsubscribe Subscription
    public static void unsubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    //Helper method to unsubscribe CompositeSubscription
    public static void unsubscribe(CompositeSubscription compositeSubscription) {
        if (compositeSubscription != null &&
                !compositeSubscription.isUnsubscribed() &&
                compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }
}
