package com.luseen.yandexsummerschool.ui.activity.root;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.jakewharton.rxbinding.support.v4.view.RxViewPager;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;
import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.base.BaseActivity;
import com.luseen.yandexsummerschool.ui.adapter.MainPagerAdapter;
import com.luseen.yandexsummerschool.ui.widget.CustomBottomNavigationView;
import com.luseen.yandexsummerschool.ui.widget.NonSwappableViewPager;

import butterknife.BindView;
import rx.Subscription;

public class RootActivity extends BaseActivity<RootActivityContract.View, RootActivityContract.Presenter>
        implements RootActivityContract.View,
        OnBottomNavigationItemClickListener {

    public static final int FIRST_ITEM = 0;

    @BindView(R.id.main_view_pager)
    NonSwappableViewPager mainViewPager;

    @BindView(R.id.bottom_navigation)
    CustomBottomNavigationView bottomNavigationView;

    private Subscription viewPagerSubscription;

    public static void start(Context context) {
        Intent starter = new Intent(context, RootActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpBottomNavigation();
        setUpViewPager();
    }

    private void setUpViewPager() {
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(pagerAdapter);
        mainViewPager.setOffscreenPageLimit(3);
        mainViewPager.setPagingEnabled(false);
        viewPagerSubscription = RxViewPager.pageSelections(mainViewPager)
                .subscribe(bottomNavigationView::selectTab);
    }

    private void setUpBottomNavigation() {
        BottomNavigationItem translationTab = new BottomNavigationItem(getString(R.string.translate_tab),
                R.color.blue, R.drawable.ic_tab_translate);
        BottomNavigationItem favTab = new BottomNavigationItem(getString(R.string.favourite_tab),
                R.color.blue, R.drawable.remove_fav_anim_icon);
        BottomNavigationItem settingsTab = new BottomNavigationItem(getString(R.string.settings_tab),
                R.color.blue, R.drawable.ic_tab_settings);
        bottomNavigationView.addTab(translationTab);
        bottomNavigationView.addTab(favTab);
        bottomNavigationView.addTab(settingsTab);
        bottomNavigationView.isColoredBackground(false);
        int activeColor = ContextCompat.getColor(this, R.color.colorPrimary);
        bottomNavigationView.setItemActiveColorWithoutColoredBackground(activeColor);
        bottomNavigationView.setOnBottomNavigationItemClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewPagerSubscription != null) {
            viewPagerSubscription.unsubscribe();
        }
    }

    @Override
    public void onBackPressed() {
        if (mainViewPager.getCurrentItem() != FIRST_ITEM) {
            mainViewPager.setCurrentItem(FIRST_ITEM);
        } else {
            super.onBackPressed();
        }
    }

    @NonNull
    @Override
    public RootActivityContract.Presenter createPresenter() {
        return new RootActivityPresenter();
    }

    @Override
    public void onNavigationItemClick(int index) {
        mainViewPager.setCurrentItem(index);
    }
}
