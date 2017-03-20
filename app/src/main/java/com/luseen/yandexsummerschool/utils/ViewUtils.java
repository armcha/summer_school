package com.luseen.yandexsummerschool.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Narek on 17.02.2017.
 */

public class ViewUtils {

    private ViewUtils() {
        //no instance
        throw new RuntimeException("Private constructor cannot be accessed");
    }

    public static void setViewMargins(View view, int dp) {
        int px = DimenUtils.dpToPx(view.getContext(), dp);
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(px, px, px, px);
    }

    /*
   left
   top
   right
   bottom*/
    public static void setViewMargins(View view, int[] margins) {
        setViewMargins(view, margins, false);
    }

    public static void setViewMargins(View view, int[] margins, boolean inDp) {
        if (!inDp) {
            for (int i = 0; i < margins.length; i++) {
                margins[i] = DimenUtils.dpToPx(view.getContext(), margins[i]);
            }
        }
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams())
                .setMargins(margins[0], margins[1], margins[2], margins[3]);
    }
}
