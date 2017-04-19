package com.luseen.yandexsummerschool.utils;

import android.widget.Toast;

import com.luseen.yandexsummerschool.App;
import com.luseen.yandexsummerschool.BuildConfig;

/**
 * Created by Chatikyan on 21.11.2016.
 */

public class ExceptionTracker {

    private ExceptionTracker() {
        throw new RuntimeException("Private constructor cannot be accessed");
    }

    public static void trackException(Throwable throwable) {
        Logger.e("EXCEPTION TRACKER HAS BAD MESSAGE FOR YOU - " + throwable.getMessage());
        if (BuildConfig.DEBUG)
            Toast.makeText(App.getInstance(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
