package com.luseen.yandexsummerschool.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.luseen.yandexsummerschool.ui.fragment.history_and_favourite_root.HistoryAndFavouriteRootFragment;
import com.luseen.yandexsummerschool.ui.fragment.about.AboutFragment;
import com.luseen.yandexsummerschool.ui.fragment.translation.TranslationFragment;

/**
 * Created by Chatikyan on 18.03.2017.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    public static final int TRANSLATION_POSITION = 0;
    public static final int HISTORY_POSITION = 1;
    private static final int PAGE_COUNT = 3;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == TRANSLATION_POSITION)
            return TranslationFragment.newInstance();
        else if (position == HISTORY_POSITION)
            return HistoryAndFavouriteRootFragment.newInstance();
        else
            return AboutFragment.newInstance();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
