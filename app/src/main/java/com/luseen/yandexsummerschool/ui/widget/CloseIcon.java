package com.luseen.yandexsummerschool.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.utils.AnimationUtils;

/**
 * Created by Chatikyan on 20.03.2017.
 */

public class CloseIcon extends AppCompatImageView implements View.OnClickListener, Viewable, AnimatableView {

    public static final long ANIMATION_DURATION = 150L;

    public interface CloseIconClickListener {
        void onClosePressed(CloseIcon closeIcon);
    }

    private CloseIconClickListener closeIconClickListener;

    public CloseIcon(Context context) {
        super(context);
        init(context);
    }

    @Override
    public void init(Context context) {
        Drawable backGround = ContextCompat.getDrawable(context, R.drawable.ripple_bg);
        setVisibility(GONE);
        setBackground(backGround);
        setImageResource(R.drawable.close_icon);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (closeIconClickListener != null) {
            closeIconClickListener.onClosePressed(this);
        }
    }

    @Override
    public void show() {
        if (getVisibility() == VISIBLE) {
            return;
        }
        setVisibility(VISIBLE);
        setScaleX(0);
        setScaleY(0);

        ViewCompat.animate(this)
                .setDuration(ANIMATION_DURATION)
                .scaleX(1)
                .scaleY(1)
                .setInterpolator(AnimationUtils.getFastOutSlowInInterpolator())
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(View view) {
                        setVisibility(VISIBLE);
                    }
                })
                .start();
    }

    @Override
    public void hide() {
        ViewCompat.animate(this)
                .setDuration(ANIMATION_DURATION)
                .scaleX(0)
                .scaleY(0)
                .setInterpolator(AnimationUtils.getFastOutSlowInInterpolator())
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(final View view) {
                        setVisibility(GONE);
                    }
                })
                .start();
    }

    public void setCloseIconClickListener(CloseIconClickListener closeIconClickListener) {
        this.closeIconClickListener = closeIconClickListener;
    }
}
