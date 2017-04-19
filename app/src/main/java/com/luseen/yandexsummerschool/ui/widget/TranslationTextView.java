package com.luseen.yandexsummerschool.ui.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.luseen.yandexsummerschool.R;

/**
 * Created by Chatikyan on 24.03.2017.
 */

public class TranslationTextView extends FontTextView {

    public TranslationTextView(Context context) {
        super(context);
    }

    public TranslationTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        int translationTextLeftPadding = (int) getResources().getDimension(R.dimen.translation_text_left_padding);
        setPadding(translationTextLeftPadding, 0, 0, 0);
    }

    public void reset() {
        setText(null);
    }
}
