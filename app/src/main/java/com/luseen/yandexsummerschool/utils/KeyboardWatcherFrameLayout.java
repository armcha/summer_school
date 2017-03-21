package com.luseen.yandexsummerschool.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * Created by Chatikyan on 20.03.2017.
 */

public class KeyboardWatcherFrameLayout extends FrameLayout {

    private final static int KEYBOARD_VISIBLE_THRESHOLD_DP = 100;

    public KeyboardWatcherFrameLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public KeyboardWatcherFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
    }

    final ViewTreeObserver.OnGlobalLayoutListener layoutListener =
            new ViewTreeObserver.OnGlobalLayoutListener() {
                private final Rect r = new Rect();
                private final int visibleThreshold = Math.round(DimenUtils.dpToPx(getContext(), KEYBOARD_VISIBLE_THRESHOLD_DP));
                private boolean wasOpened = false;

                @Override
                public void onGlobalLayout() {
                    getWindowVisibleDisplayFrame(r);

                    int heightDiff = getRootView().getHeight() - r.height();
                    boolean isOpen = heightDiff > visibleThreshold;
                    if (isOpen == wasOpened) {
                        return;
                    }

                    wasOpened = isOpen;
                    Logger.log(isOpen);
                }
            };
}
