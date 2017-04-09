package com.luseen.yandexsummerschool.utils;

import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;

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

    public static void makeRotateAnimation(View view) {
        RotateAnimation rotate = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setInterpolator(getFastOutSlowInInterpolator());
        view.startAnimation(rotate);
    }

    public static AnimatedVectorDrawableCompat createAnimatedVector(int vectorId) {
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat =
                AnimatedVectorDrawableCompat.create(App.getInstance(), vectorId);
        return animatedVectorDrawableCompat;
    }
}
