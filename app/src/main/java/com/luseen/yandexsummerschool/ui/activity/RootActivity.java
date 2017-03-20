package com.luseen.yandexsummerschool.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.api.ApiActivity;
import com.luseen.yandexsummerschool.ui.adapter.MainPagerAdapter;
import com.luseen.yandexsummerschool.ui.widget.IconicTabLayout;

import butterknife.BindView;

public class RootActivity extends ApiActivity<RootActivityContract.View, RootActivityContract.Presenter>
        implements RootActivityContract.View {

    @BindView(R.id.main_view_pager)
    ViewPager mainViewPager;

    @BindView(R.id.tab_layout)
    IconicTabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViewPager();
    }

    private void setUpViewPager() {
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(pagerAdapter);
        mainViewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(mainViewPager);
    }

    @NonNull
    @Override
    public RootActivityContract.Presenter createPresenter() {
        return new RootActivityPresenter();
    }

}
