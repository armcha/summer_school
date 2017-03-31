package com.luseen.yandexsummerschool.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.luseen.yandexsummerschool.utils.AnimationUtils;

/**
 * Created by Chatikyan on 14.03.2017.
 */

public class YaProgressView extends View implements Viewable {

    private static final long ANIMATION_START_DELAY = 500L;
    private static final int HIDE_SHOW_DURATION = 100;
    private static final int ROTATE_DURATION = 1000;
    private static final float SWEEP_ANGLE = 180;
    private static final float START_ANGLE = 0;

    private int progressColor = Color.GREEN;
    private RotateAnimation rotateAnimation;
    private Paint progressPaint;
    private RectF rectF;

    public YaProgressView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public YaProgressView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public void init(Context context) {
        setBackgroundColor(Color.TRANSPARENT);
        setVisibility(GONE);
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(progressColor);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float progressStrokeWidth = getWidth() / 16;
        progressPaint.setStrokeWidth(progressStrokeWidth);
        float progressThickness = progressStrokeWidth / 2;
        rectF.set(progressThickness, progressThickness,
                getWidth() - progressThickness, getHeight() - progressThickness);
        canvas.drawArc(rectF, START_ANGLE, SWEEP_ANGLE, false, progressPaint);
    }

    public void show() {
        if (getVisibility() == VISIBLE)
            return;

        ViewCompat.animate(this)
                .setDuration(HIDE_SHOW_DURATION)
                .scaleX(1)
                .scaleY(1)
                .setInterpolator(AnimationUtils.getFastOutSlowInInterpolator())
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(View view) {
                        setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationStart(View view) {
                        super.onAnimationStart(view);
                        setVisibility(VISIBLE);
                        setScaleX(0);
                        setScaleY(0);

                        rotateAnimation = getRotateAnimation();
                        startAnimation(rotateAnimation);
                    }
                })
                .start();
    }

    private RotateAnimation getRotateAnimation() {
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(ROTATE_DURATION);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
        return rotate;
    }

    public void hide() {
        if (getVisibility() == GONE)
            return;

        ViewCompat.animate(this)
                .setDuration(HIDE_SHOW_DURATION)
                .scaleX(0)
                .scaleY(0)
                .setInterpolator(AnimationUtils.getFastOutSlowInInterpolator())
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(final View view) {
                        setVisibility(GONE);
                        if (rotateAnimation != null) {
                            rotateAnimation.cancel();
                        }
                    }
                })
                .start();
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        progressPaint.setColor(progressColor);
        postInvalidate();
    }
}
