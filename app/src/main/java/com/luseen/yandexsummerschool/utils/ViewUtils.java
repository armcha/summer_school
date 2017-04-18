package com.luseen.yandexsummerschool.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.luseen.yandexsummerschool.R;

import java.lang.reflect.Field;

/**
 * Created by Narek on 17.02.2017.
 */

public class ViewUtils {

    private ViewUtils() {
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

    // There is no public API to set the cursor drawable.
    // The field mCursorDrawableRes hasn't changed so this should work on all devices,
    // unless a manufacturer changed something or it is later changed.
    public static void setEditTextDefaultCursorDrawable(EditText editText) {
        Field cursorDrawableField = null;
        try {
            cursorDrawableField = TextView.class.getDeclaredField("mCursorDrawableRes");
            cursorDrawableField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            ExceptionTracker.trackException(e);
        }
        try {
            if (cursorDrawableField != null) {
                cursorDrawableField.set(editText, R.drawable.cursor_drawable);
            }
        } catch (IllegalAccessException e) {
            ExceptionTracker.trackException(e);
        }
    }
}
