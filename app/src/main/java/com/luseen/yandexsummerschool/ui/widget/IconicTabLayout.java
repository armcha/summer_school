package com.luseen.yandexsummerschool.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.luseen.yandexsummerschool.R;

/**
 * Created by Chatikyan on 18.03.2017.
 */

public class IconicTabLayout extends TabLayout implements TabLayout.OnTabSelectedListener {

    final int[] icons = {R.drawable.ic_tab_translate, R.drawable.ic_tab_fav, R.drawable.ic_tab_settings};
    private final int selectedIconColor = Color.RED;
    private final int unSelectedIconColor = Color.BLUE;

    public IconicTabLayout(Context context) {
        super(context);
        init();
    }

    public IconicTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setSelectedTabIndicatorHeight(30);
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager);
        setUpIcons();
        addOnTabSelectedListener(this);
    }

    public void setUpIcons() {
        for (int i = 0; i < icons.length; i++) {
            Tab tab = getTabAt(i);
            tab.setIcon(icons[i]);
            tab.getIcon().setColorFilter(selectedIconColor, PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public void onTabSelected(Tab tab) {
        tab.getIcon().setColorFilter(selectedIconColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onTabUnselected(Tab tab) {
        tab.getIcon().setColorFilter(unSelectedIconColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onTabReselected(Tab tab) {

    }
}
