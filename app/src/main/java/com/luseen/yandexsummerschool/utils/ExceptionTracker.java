package com.luseen.yandexsummerschool.utils;

/**
 * Created by Chatikyan on 21.11.2016.
 */

public class ExceptionTracker {

    public static void trackException(Throwable throwable) {
        Logger.log("EXCEPTION TRACKER HAS BAD MESSAGE FOR YOU - " + throwable.getMessage());
    }
}
