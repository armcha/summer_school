package com.luseen.yandexsummerschool.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.luseen.yandexsummerschool.App;
import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.ui.fragment.favourite.FavouriteFragment;
import com.luseen.yandexsummerschool.ui.fragment.history.HistoryFragment;

/**
 * Created by Chatikyan on 18.03.2017.
 */

public class HistoryAndFavouritePagerAdapter extends FragmentPagerAdapter {

    public static final int HISTORY_POSITION = 0;
    public static final int FAVOURITE_POSITION = 1;
    private static final int PAGE_COUNT = 2;
    private String[] titles = new String[2];

    public HistoryAndFavouritePagerAdapter(FragmentManager fm) {
        super(fm);
        titles[0] = App.getInstance().getString(R.string.history);
        titles[1] = App.getInstance().getString(R.string.favourite);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == HISTORY_POSITION)
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
