package com.luseen.yandexsummerschool.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.luseen.yandexsummerschool.ui.fragment.TestFragment;
import com.luseen.yandexsummerschool.ui.fragment.history_and_favourite_root.HistoryAndFavouriteRootFragment;
import com.luseen.yandexsummerschool.ui.fragment.translation.TranslationFragment;

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
        // TODO: 30.03.2017 add real fragments
        if (position == 0)
            return TranslationFragment.newInstance();
        else if (position == 1)
            return HistoryAndFavouriteRootFragment.newInstance();
        else return new TestFragment();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
