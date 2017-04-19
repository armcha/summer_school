package com.luseen.yandexsummerschool.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luseen.yandexsummerschool.utils.FontUtils;

import static com.luseen.yandexsummerschool.utils.AppConstants.SANS_LIGHT;

/**
 * Created by Chatikyan on 31.03.2017.
 */

public class FontTabLayout extends TabLayout {

    public FontTabLayout(Context context) {
        super(context);
    }

    public FontTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addTab(@NonNull Tab tab, int position, boolean setSelected) {
        super.addTab(tab, position, setSelected);
        ViewGroup mainView = (ViewGroup) getChildAt(0);
        ViewGroup tabView = (ViewGroup) mainView.getChildAt(tab.getPosition());

        int tabChildCount = tabView.getChildCount();
        for (int i = 0; i < tabChildCount; i++) {
            View tabViewChild = tabView.getChildAt(i);
            if (tabViewChild instanceof TextView) {
                TextView childTextView = ((TextView) tabViewChild);
                childTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                childTextView.setTypeface(FontUtils.get(getContext(), SANS_LIGHT));
            }
        }
    }
}

