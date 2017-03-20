package com.luseen.yandexsummerschool.utils;

import android.os.Build;

/**
 * Created by Chatikyan on 18.03.2017.
 */

public class CommonUtils {

    private CommonUtils() {
        throw new RuntimeException("Private constructor cannot be accessed");
    }

    public static boolean isLollipopOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isMarshmallowOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
