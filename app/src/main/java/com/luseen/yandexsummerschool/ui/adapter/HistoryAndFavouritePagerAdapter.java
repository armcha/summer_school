package com.luseen.yandexsummerschool.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

import com.luseen.yandexsummerschool.App;
import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.ui.fragment.favourite.FavouriteFragment;
import com.luseen.yandexsummerschool.ui.fragment.history.HistoryFragment;
import com.luseen.yandexsummerschool.utils.Logger;

/**
 * Created by Chatikyan on 18.03.2017.
 */

public class HistoryAndFavouritePagerAdapter extends FragmentPagerAdapter {

    private static final int HISTORY_POSITION = 0;
    private static final int PAGE_COUNT = 2;

    private final SparseArrayCompat<Fragment> fragmentSparseArray = new SparseArrayCompat<>(2);
    private final String[] titles = new String[2];

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
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        if (fragmentSparseArray.get(position) == null)
            fragmentSparseArray.put(position, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public Fragment getFragmentByIndex(int index) {
        return fragmentSparseArray.get(index);
    }
}
