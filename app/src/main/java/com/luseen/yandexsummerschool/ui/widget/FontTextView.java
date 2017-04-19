package com.luseen.yandexsummerschool.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.utils.FontUtils;
import com.luseen.yandexsummerschool.utils.Logger;

/**
 * Created by Chatikyan on 16.02.2017.
 */

public class FontTextView extends AppCompatTextView {

    public FontTextView(Context context) {
        super(context);
        init(context, null);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);

        if (typedArray.hasValue(R.styleable.FontTextView_android_textAppearance)) {
            final int textAppearanceId = typedArray.getResourceId(R.styleable
                            .FontTextView_android_textAppearance,
                    android.R.style.TextAppearance);
            TypedArray atp = getContext().obtainStyledAttributes(textAppearanceId,
                    R.styleable.FontTextAppearance);
            if (atp.hasValue(R.styleable.FontTextAppearance_font)) {
                setFont(atp.getString(R.styleable.FontTextAppearance_font));
            }
            atp.recycle();
        }

        if (typedArray.hasValue(R.styleable.FontTextView_font)) {
            setFont(typedArray.getString(R.styleable.FontTextView_font));
        }
        typedArray.recycle();
    }

    public void setFont(String font) {
        setPaintFlags(getPaintFlags() | Paint.ANTI_ALIAS_FLAG);
        setTypeface(FontUtils.get(getContext(), font));
    }
}
