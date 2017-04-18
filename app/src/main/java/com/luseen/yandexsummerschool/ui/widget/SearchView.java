package com.luseen.yandexsummerschool.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.utils.CommonUtils;
import com.luseen.yandexsummerschool.utils.DimenUtils;
import com.luseen.yandexsummerschool.utils.KeyboardUtils;
import com.luseen.yandexsummerschool.utils.StringUtils;
import com.luseen.yandexsummerschool.utils.ViewUtils;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Chatikyan on 31.03.2017.
 */

public class SearchView extends RelativeLayout implements Viewable, View.OnClickListener,
        CloseIcon.CloseIconClickListener {

    public interface SearchListener {
        void onTextChange(String input);

        void onResetClicked();

        void onEmptyInput();
    }

    public static final String SEARCH_TEXT_STATE_KEY = "search_text_state_key";

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
                .skip(1)
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
        setOnClickListener(__ -> {
            if (!isEnable) enable();
        });
        disable();
    }

    public void onSaveInstance(Bundle outState) {
        outState.putString(SEARCH_TEXT_STATE_KEY, searchEditText.getText().toString());
    }

    public void restoreInstance(Bundle savedInstance) {
        if (CommonUtils.bundleContainsKeyAndNonNull(savedInstance, SEARCH_TEXT_STATE_KEY)) {
            String inputText = savedInstance.getString(SEARCH_TEXT_STATE_KEY);
            resetIcon.show();
            searchEditText.setText(inputText);
        }
    }

    private void addBottomDivider(Context context) {
        divider = new View(context);
        divider.setBackgroundColor(inActiveBorderColor);
        addView(divider);
        int dividerHeight = DimenUtils.dpToPx(context, 1);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, dividerHeight);
        params.addRule(ALIGN_PARENT_BOTTOM);
        divider.setLayoutParams(params);
    }

    private void addSearchIcon(Context context) {
        // FIXME: 11.04.2017 Magnify not showing in pre lollipop
        searchIcon = new ImageView(context);
        searchIcon.setImageResource(R.drawable.magnify);
        searchIcon.setId(SEARCH_ICON_ID);
        searchIcon.setOnClickListener(this);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(CENTER_VERTICAL);
        addView(searchIcon, params);
        ViewUtils.setViewMargins(searchIcon, new int[]{10, 0, 10, 0});
    }

    private void addSearchEditText(Context context) {
        searchEditText = new EditText(context);
        searchEditText.setId(SEARCH_EDIT_TEXT);
        searchEditText.setBackground(null);
        searchEditText.setSingleLine(true);
        searchEditText.setOnClickListener(this);
        searchEditText.setHintTextColor(inActiveBorderColor);
        searchEditText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        ViewUtils.setEditTextDefaultCursorDrawable(searchEditText);
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

    public void enable() {
        enableEditText();
        divider.setBackgroundColor(activeBorderColor);
        KeyboardUtils.showKeyboard(this);
        isEnable = true;
    }

    public void disable() {
        disableEditText();
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

    public String getSearchText() {
        return searchEditText.getText().toString();
    }

    public void reset() {
        searchEditText.setText(StringUtils.EMPTY);
        disable();
    }
}
