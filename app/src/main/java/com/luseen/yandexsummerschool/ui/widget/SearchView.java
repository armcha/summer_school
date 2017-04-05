package com.luseen.yandexsummerschool.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.utils.AnimationUtils;
import com.luseen.yandexsummerschool.utils.DimenUtils;
import com.luseen.yandexsummerschool.utils.ViewUtils;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Chatikyan on 31.03.2017.
 */

public class SearchView extends RelativeLayout implements Viewable,
        View.OnClickListener,
        CloseIcon.CloseIconClickListener {

    public interface SearchListener {
        void onTextChange(String input);

        void onResetClicked();

        void onEmptyInput();
    }

    @IdRes
    public static final int SEARCH_ICON_ID = 1;
    @IdRes
    public static final int SEARCH_EDIT_TEXT = 2;
    @IdRes
    public static final int RESET_ICON = 3;

    private Subscription textChangeSubscription;
    private SearchListener searchListener;
    private EditText searchEditText;
    private ImageView searchIcon;
    private CloseIcon resetIcon;
    private View divider;

    private int inActiveBorderColor;
    private int activeBorderColor;
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
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        textChangeSubscription = RxTextView.textChanges(searchEditText)
                .observeOn(AndroidSchedulers.mainThread())
                .map(CharSequence::toString)
                .map(String::trim)
                .doOnNext(s -> {
                    if (s.length() > 0) {
                        resetIcon.show();
                    } else {
                        resetIcon.hide();
                        if (searchListener != null) {
                            searchListener.onEmptyInput();
                        }
                    }
                })
                .filter(input -> !input.isEmpty())
                .subscribe(input -> {
                    if (searchListener != null) {
                        searchListener.onTextChange(input);
                    }
                });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (textChangeSubscription != null) {
            textChangeSubscription.unsubscribe();
        }
    }

    @Override
    public void init(Context context) {
        activeBorderColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        inActiveBorderColor = ContextCompat.getColor(getContext(), R.color.light_gray);
        addSearchIcon(context);
        addSearchEditText(context);
        addResetIcon(context);
        addBottomDivider(context);
        setOnClickListener(v -> {
            if (!isEnable) enable();
        });
        disable();
    }

    private void addBottomDivider(Context context) {
        divider = new View(context);
        divider.setBackgroundColor(inActiveBorderColor);
        addView(divider);
        //some hack 0.5 dp
        int dividerHeight = DimenUtils.dpToPx(context, 1);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, dividerHeight);
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
        searchEditText.setBackground(null);
        searchEditText.setSingleLine(true);
        searchEditText.setOnClickListener(this);
        //underline thickness
        searchEditText.setPadding(0, 2, 0, 0);
        searchEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        addView(searchEditText);
        LayoutParams params = ((LayoutParams) searchEditText.getLayoutParams());
        params.addRule(RIGHT_OF, searchIcon.getId());
        params.addRule(CENTER_VERTICAL);
        searchEditText.setLayoutParams(params);
        ViewUtils.setViewMargins(searchEditText, new int[]{0, 0, 50, 0});
    }

    private void addResetIcon(Context context) {
        resetIcon = new CloseIcon(context);
        resetIcon.setId(RESET_ICON);
        resetIcon.setCloseIconClickListener(this);
        addView(resetIcon);
        int iconSize = DimenUtils.dpToPx(context, 50);
        LayoutParams params = new LayoutParams(iconSize, iconSize);
        int padding = DimenUtils.dpToPx(context, 10);
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
        divider.setBackgroundColor(activeBorderColor);
        isEnable = true;
    }

    public void disable() {
        disableEditText();
        //setDividerEnabled(false);
        divider.setBackgroundColor(inActiveBorderColor);
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
        if (searchListener != null) {
            searchListener.onResetClicked();
        }
    }

    public void setHint(String hint) {
        searchEditText.setHint(hint);
    }

    public void setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }
}
