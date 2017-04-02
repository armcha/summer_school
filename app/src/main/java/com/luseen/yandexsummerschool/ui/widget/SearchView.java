package com.luseen.yandexsummerschool.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.utils.AnimationUtils;
import com.luseen.yandexsummerschool.utils.DimenUtils;
import com.luseen.yandexsummerschool.utils.ViewUtils;

/**
 * Created by Chatikyan on 31.03.2017.
 */

public class SearchView extends RelativeLayout implements Viewable,
        View.OnClickListener,
        CloseIcon.CloseIconClickListener {

    @IdRes
    public static final int SEARCH_ICON_ID = 1;
    @IdRes
    public static final int SEARCH_EDIT_TEXT = 2;
    @IdRes
    public static final int RESET_ICON = 3;

    private ImageView searchIcon;
    private EditText searchEditText;
    private CloseIcon resetIcon;
    private View divider;
    private boolean isEnable;

    public SearchView(Context context) {
        super(context);
        init(context);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public void init(Context context) {
        addSearchIcon(context);
        addSearchEditText(context);
        addResetIcon(context);
        addBottomDivider(context);
        setOnClickListener(v -> {
            if (!isEnable) enable();
        });
        disable();
        resetIcon.show();
    }

    private void addBottomDivider(Context context) {
        divider = new View(context);
        divider.setBackgroundColor(Color.GRAY);
        addView(divider);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, 3);
        params.addRule(ALIGN_PARENT_BOTTOM);
        divider.setLayoutParams(params);
    }

    private void addSearchIcon(Context context) {
        searchIcon = new ImageView(context);
        searchIcon.setImageResource(R.drawable.magnify);
        searchIcon.setId(SEARCH_ICON_ID);
        searchIcon.setOnClickListener(this);
        addView(searchIcon);
        LayoutParams params = ((LayoutParams) searchIcon.getLayoutParams());
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(CENTER_VERTICAL);
        searchIcon.setLayoutParams(params);
        ViewUtils.setViewMargins(searchIcon, new int[]{10, 0, 10, 0});
    }

    private void addSearchEditText(Context context) {
        searchEditText = new EditText(context);
        searchEditText.setId(SEARCH_EDIT_TEXT);
        searchEditText.setHint("Search");
        searchEditText.setBackground(null);
        searchEditText.setSingleLine(true);
        searchEditText.setOnClickListener(this);
        //underline thickness
        searchEditText.setPadding(0, 2, 0, 0);
        searchEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        addView(searchEditText);
        LayoutParams params = ((LayoutParams) searchEditText.getLayoutParams());
        params.addRule(RIGHT_OF, searchIcon.getId());
        params.addRule(CENTER_VERTICAL);
        searchEditText.setLayoutParams(params);
        ViewUtils.setViewMargins(searchEditText, new int[]{0, 0, 40, 0});
    }

    private void addResetIcon(Context context) {
        resetIcon = new CloseIcon(context);
        resetIcon.setId(RESET_ICON);
        resetIcon.setCloseIconClickListener(this);
        addView(resetIcon);
        int iconSize = DimenUtils.dpToPx(context, 40);
        LayoutParams params = new LayoutParams(iconSize, iconSize);
        int padding = DimenUtils.dpToPx(context, 7);
        resetIcon.setPadding(padding, padding, padding, padding);
        params.addRule(ALIGN_PARENT_RIGHT);
        params.addRule(CENTER_VERTICAL);
        resetIcon.setLayoutParams(params);
    }

    private void disableEditText() {
        searchEditText.setCursorVisible(false);
        searchEditText.clearFocus();
    }

    private void enableEditText() {
        searchEditText.setCursorVisible(true);
        searchEditText.requestFocus();
    }

    private void setDividerEnabled(boolean enabled) {
        ValueAnimator animator = ValueAnimator.ofFloat(enabled ? 0.7f : 1, enabled ? 1 : 0.7f);
        animator.setDuration(500);
        animator.setInterpolator(AnimationUtils.getFastOutSlowInInterpolator());
        animator.addUpdateListener(valueAnimator -> {
            float animatedValue = (float) valueAnimator.getAnimatedValue();
            divider.setScaleY(animatedValue);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                divider.setScaleY(enabled ? 1 : 0.7f);
                divider.setBackgroundColor(enabled ? Color.BLUE : Color.GRAY);
            }
        });
        animator.start();
    }

    public void enable() {
        enableEditText();
        //setDividerEnabled(true);
        divider.setBackgroundColor(Color.BLUE);
        isEnable = true;
    }

    public void disable() {
        disableEditText();
        //setDividerEnabled(false);
        divider.setBackgroundColor(Color.GRAY);
        isEnable = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case SEARCH_ICON_ID:
            case SEARCH_EDIT_TEXT:
                if (!isEnable) {
                    enable();
                }
                break;
        }
    }

    public boolean isEnable() {
        return isEnable;
    }

    @Override
    public void onClosePressed(CloseIcon closeIcon) {
        searchEditText.setText(null);
        if (!isEnable) {
            enable();
        }
    }
}
