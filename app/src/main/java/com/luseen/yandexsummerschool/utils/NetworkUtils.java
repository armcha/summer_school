package com.luseen.yandexsummerschool.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.luseen.yandexsummerschool.App;

/**
 * Created by Chatikyan on 12.04.2017.
 */

public class NetworkUtils {

    private NetworkUtils() {
        throw new RuntimeException("Private constructor cannot be accessed");
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return CommonUtils.nonNull(activeNetworkInfo);
    }
}
