package com.luseen.yandexsummerschool.utils;

import android.view.animation.Interpolator;

import com.luseen.yandexsummerschool.App;


/**
 * Created by Chatikyan on 16.02.2017.
 */

public class AnimationUtils {

    private AnimationUtils() {
        throw new AssertionError("No instances");
    }

    public static Interpolator getFastOutSlowInInterpolator() {
        if (!CommonUtils.isLollipopOrHigher()) {
            return android.view.animation.AnimationUtils.loadInterpolator(App.getInstance(),
                    android.R.interpolator.linear);
        }
        return android.view.animation.AnimationUtils.loadInterpolator(App.getInstance(),
                android.R.interpolator.fast_out_slow_in);
    }
}
