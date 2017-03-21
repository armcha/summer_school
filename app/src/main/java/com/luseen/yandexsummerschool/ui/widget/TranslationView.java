package com.luseen.yandexsummerschool.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.luseen.yandexsummerschool.utils.AnimationUtils;
import com.luseen.yandexsummerschool.utils.KeyboardUtils;
import com.luseen.yandexsummerschool.utils.StringUtils;
import com.luseen.yandexsummerschool.utils.ViewUtils;

/**
 * Created by Chatikyan on 20.03.2017.
 */

public class TranslationView extends RelativeLayout implements View.OnClickListener,
        CloseIcon.CloseIconClickListener {

    private boolean isEnable = false;
    private boolean isCloseIconShown = false;
    private int activeBorderColor = Color.BLUE;
    private int inActiveBorderColor = Color.GRAY;
    private int activeBorderWidth = 10;
    private int inActiveBorderWidth = 5;
    private EditText translationEditText;
    private CloseIcon closeIcon;

    public TranslationView(Context context) {
        super(context);
        init(context);
    }

    public TranslationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setOnClickListener(this);
        addEditText(context);
        addCloseIcon(context);
    }

    private void addEditText(Context context) {
        translationEditText = new EditText(context);
        addView(translationEditText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LayoutParams params = (LayoutParams) translationEditText.getLayoutParams();
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(ALIGN_PARENT_TOP);
        ViewUtils.setViewMargins(translationEditText, new int[]{10, 0, 40, 50});
        translationEditText.setHint("Type text");
        translationEditText.setBackground(null);
        translationEditText.setOnClickListener(this);
        translationEditText.addTextChangedListener(new AbstractTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (count > 0 && !isCloseIconShown) {
                    closeIcon.show();
                } else if (s.length() == 0) {
                    closeIcon.hide();
                }
            }
        });
    }

    private void addCloseIcon(Context context) {
        closeIcon = new CloseIcon(context);
        addView(closeIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LayoutParams params = (LayoutParams) closeIcon.getLayoutParams();
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(ALIGN_PARENT_BOTTOM);
        ViewUtils.setViewMargins(closeIcon, new int[]{7, 0, 0, 7});
        closeIcon.setCloseIconClickListener(this);
    }

    private void setBackgroundShape(int borderWidth) {
        GradientDrawable shape = getGradientDrawable();
        shape.setStroke(borderWidth, activeBorderColor);
        setBackground(shape);
    }

    private void changeBackgroundShapeColor(int strokeColor) {
        GradientDrawable shape = getGradientDrawable();
        shape.setStroke(isEnable ? activeBorderWidth : inActiveBorderWidth, strokeColor);
        setBackground(shape);
    }

    private GradientDrawable getGradientDrawable() {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(Color.WHITE);
        shape.setCornerRadius(5);
        return shape;
    }

    private void changeBorderWidth(int fromWidth, int toWidth) {
        ValueAnimator animator = ValueAnimator.ofInt(fromWidth, toWidth);
        animator.setDuration(200);
        animator.setInterpolator(AnimationUtils.getFastOutSlowInInterpolator());
        animator.addUpdateListener(valueAnimator -> {
            int animatedValue = (int) valueAnimator.getAnimatedValue();
            setBackgroundShape(animatedValue);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                int targetColor = isEnable ? activeBorderColor : inActiveBorderColor;
                changeBackgroundShapeColor(targetColor);
            }
        });
        animator.start();
    }

    @Override
    public void onClick(View v) {
        if (!isEnable) enable();
    }

    private void disableEditText() {
        translationEditText.setCursorVisible(false);
        translationEditText.clearFocus();
    }

    private void enableEditText() {
        translationEditText.setCursorVisible(true);
        translationEditText.requestFocus();
    }

    public void disable() {
        disableEditText();
        changeBorderWidth(activeBorderWidth, inActiveBorderWidth);
        KeyboardUtils.hideKeyboard(this);
        isEnable = false;
    }

    public void enable() {
        enableEditText();
        changeBorderWidth(inActiveBorderWidth, activeBorderWidth);
        KeyboardUtils.showKeyboard(this);
        isEnable = true;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public EditText getTranslationEditText() {
        return translationEditText;
    }

    @Override
    public void onClosePressed(CloseIcon closeIcon) {
        translationEditText.setText(StringUtils.EMPTY);
        closeIcon.hide();
        if (!isEnable) enable();
    }

    public CloseIcon getCloseIcon() {
        return closeIcon;
    }
}
