package com.luseen.yandexsummerschool.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.ui.fragment.TranslationFragment;
import com.luseen.yandexsummerschool.ui.widget.IconicTabLayout;

/**
 * Created by Chatikyan on 18.03.2017.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 3;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TranslationFragment.newInstance();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
