package com.luseen.yandexsummerschool.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Chatikyan on 31.03.2017.
 */

public class FontTabLayout extends TabLayout implements Viewable {

    public FontTabLayout(Context context) {
        super(context);
    }

    public FontTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        //Maybe in future we need custom fonts
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
                childTextView.setTypeface((Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)));
            }
        }
    }
}

