package com.luseen.yandexsummerschool.ui.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.utils.AnimationUtils;
import com.luseen.yandexsummerschool.utils.ViewUtils;

/**
 * Created by Chatikyan on 06.04.2017.
 */

public class InfoShowerCoordinatorLayout extends CoordinatorLayout implements Viewable, InfoShower {

    private static final long ANIMATION_DURATION = 200L;
    private AppCompatImageView infoIconImageView;
    private FontTextView infoTextView;
    private View rootView;
    private boolean whitTopMargin = false;

    public InfoShowerCoordinatorLayout(Context context) {
        super(context);
        init(context);
    }

    public InfoShowerCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        rootView = inflater.inflate(R.layout.info_view, this, false);
        infoIconImageView = (AppCompatImageView) rootView.findViewById(R.id.info_icon);
        infoTextView = (FontTextView) rootView.findViewById(R.id.info_text);
        rootView.setVisibility(GONE);
        addView(rootView);

        hide();
    }

    @Override
    public void setInfoText(String infoText) {
        infoTextView.setText(infoText);
    }

    @Override
    public void setInfoIcon(int infoIcon) {
        infoIconImageView.setImageResource(infoIcon);
    }

    @Override
    public void show() {
        if (rootView.getVisibility() == VISIBLE) return;

        rootView.setVisibility(VISIBLE);
        rootView.setScaleX(0);
        rootView.setScaleY(0);

        ViewCompat.animate(rootView)
                .setDuration(ANIMATION_DURATION)
                .scaleX(1)
                .scaleY(1)
                .setInterpolator(AnimationUtils.getFastOutSlowInInterpolator())
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(View view) {
                        rootView.setVisibility(VISIBLE);
                    }
                })
                .start();
    }

    @Override
    public void hide() {
        ViewCompat.animate(rootView)
                .setDuration(ANIMATION_DURATION)
                .scaleX(0)
                .scaleY(0)
                .setInterpolator(AnimationUtils.getFastOutSlowInInterpolator())
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(final View view) {
                        rootView.setVisibility(GONE);
                    }
                })
                .start();
    }

    public void whitTopMargin(boolean enable) {
        this.whitTopMargin = enable;
        if (enable) {
            ViewUtils.setViewMargins(rootView, new int[]{0, 70, 0, 0});
        } else {
            ViewUtils.setViewMargins(rootView, new int[]{0, 30, 0, 0});
        }
    }
}
