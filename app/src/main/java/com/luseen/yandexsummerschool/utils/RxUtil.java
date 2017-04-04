package com.luseen.yandexsummerschool.utils;

import rx.Subscription;

/**
 * Created by Chatikyan on 02.04.2017.
 */

public class RxUtil {

    private RxUtil() {
        throw new RuntimeException("Private constructor cannot be accessed");
    }

    //Helper method to unsubscribe Subscription
    public static void unsubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
