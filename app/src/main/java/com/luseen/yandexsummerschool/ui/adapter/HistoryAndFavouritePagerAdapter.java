package com.luseen.yandexsummerschool.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.luseen.yandexsummerschool.ui.fragment.favourite.FavouriteFragment;
import com.luseen.yandexsummerschool.ui.fragment.history.HistoryFragment;

/**
 * Created by Chatikyan on 18.03.2017.
 */

public class HistoryAndFavouritePagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 2;
    private String[] titles = {"History", "Favourite"};

    public HistoryAndFavouritePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return HistoryFragment.newInstance();
        else
            return FavouriteFragment.newInstance();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
