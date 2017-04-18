package com.luseen.yandexsummerschool.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

import com.luseen.yandexsummerschool.utils.AnimationUtils;


/**
 * Created by Chatikyan on 16.02.2017.
 */

public class AnimatedTextView extends FontTextView {

    public AnimatedTextView(Context context) {
        super(context);
    }

    public AnimatedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAnimatedText(CharSequence text) {
        changeText(text, 0);
    }

    public void setAnimatedText(CharSequence text, long startDelay) {
        changeText(text, startDelay);
    }

    private void changeText(final CharSequence newText, long startDelay) {
        final Interpolator fastOutSlowInterpolator = AnimationUtils.getFastOutSlowInInterpolator();
        if (getAnimation() != null) {
            getAnimation().cancel();
        }
        animate()
                .alpha(0f)
                .scaleX(0.8f)
                .setDuration(200)
                .setStartDelay(startDelay)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        setText(newText);
                        setScaleX(0.8f);
                        animate()
                                .scaleX(1f)
                                .alpha(1f)
                                .setListener(null)
                                .setStartDelay(0)
                                .setDuration(200)
                                .setInterpolator(fastOutSlowInterpolator)
                                .start();
                    }
                })
                .start();
    }

}
