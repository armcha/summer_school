package com.luseen.yandexsummerschool.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.luseen.yandexsummerschool.R;

/**
 * Created by Chatikyan on 18.03.2017.
 */

public class BottomBar extends LinearLayout implements View.OnClickListener {

    private int icons[] = {R.drawable.ic_tab_settings, R.drawable.ic_tab_settings, R.drawable.ic_tab_settings};
    private int itemCount = icons.length;

    public BottomBar(Context context) {
        super(context);
        init(context);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 0; i < itemCount; i++) {
            FrameLayout childContainer = (FrameLayout) inflater.inflate(R.layout.bottom_bar_item, this, false);
            AppCompatImageView bottomBarIcon = (AppCompatImageView) childContainer.findViewById(R.id.bottom_bar_icon);
            bottomBarIcon.setImageResource(icons[i]);
            childContainer.setTag(i);
            childContainer.setOnClickListener(this);
        }
    }

    private void createIndicator() {

    }

    @Override
    public void onClick(View v) {

    }
}
