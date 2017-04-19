package com.luseen.yandexsummerschool.ui.activity.root;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.jakewharton.rxbinding.support.v4.view.RxViewPager;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;
import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.dummy.DummyActivity;
import com.luseen.yandexsummerschool.model.event_bus_events.FromHistoryOrFavouriteEvent;
import com.luseen.yandexsummerschool.ui.adapter.MainPagerAdapter;
import com.luseen.yandexsummerschool.ui.widget.CustomBottomNavigationView;
import com.luseen.yandexsummerschool.ui.widget.NonSwappableViewPager;
import com.luseen.yandexsummerschool.utils.FontUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import rx.Subscription;

import static com.luseen.yandexsummerschool.utils.AppConstants.SANS_LIGHT;

public class RootActivity extends DummyActivity implements OnBottomNavigationItemClickListener {

    public static final String CURRENT_POSITION_KEY = "current_fragment_position_bundle_key";
    public static final int FIRST_ITEM = 0;

    @BindView(R.id.bottom_navigation)
    CustomBottomNavigationView bottomNavigationView;

    @BindView(R.id.main_view_pager)
    NonSwappableViewPager mainViewPager;

    private Subscription viewPagerSubscription;
    private int currentFragmentPosition;

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_POSITION_KEY, currentFragmentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentFragmentPosition = savedInstanceState.getInt(CURRENT_POSITION_KEY);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void receiveHistory(FromHistoryOrFavouriteEvent fromHistoryOrFavouriteEvent) {
        mainViewPager.setCurrentItem(FIRST_ITEM);
    }

    private void setUpViewPager() {
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(pagerAdapter);
        mainViewPager.setOffscreenPageLimit(3);
        mainViewPager.setPagingEnabled(false);
        viewPagerSubscription = RxViewPager.pageSelections(mainViewPager)
                .subscribe(position -> bottomNavigationView.selectTab(position));
    }

    private void setUpBottomNavigation() {
        BottomNavigationItem translationTab = new BottomNavigationItem(getString(R.string.translate_tab),
                R.color.blue, R.drawable.ic_tab_translate);
        BottomNavigationItem favTab = new BottomNavigationItem(getString(R.string.favourite_tab),
                R.color.blue, R.drawable.bookmark_check);
        BottomNavigationItem settingsTab = new BottomNavigationItem(getString(R.string.about_tab),
                R.color.blue, R.drawable.ic_about);
        float activeTextSize = getResources().getDimension(R.dimen.bottom_active_text);
        float inActiveTextSize = getResources().getDimension(R.dimen.bottom_in_active_text);
        bottomNavigationView.setTextActiveSize(activeTextSize);
        bottomNavigationView.setTextInactiveSize(inActiveTextSize);
        bottomNavigationView.addTab(translationTab);
        bottomNavigationView.addTab(favTab);
        bottomNavigationView.addTab(settingsTab);
        bottomNavigationView.isColoredBackground(false);
        int activeColor = ContextCompat.getColor(this, R.color.colorPrimary);
        bottomNavigationView.setItemActiveColorWithoutColoredBackground(activeColor);
        bottomNavigationView.setOnBottomNavigationItemClickListener(this);
        bottomNavigationView.setFont(FontUtils.get(this, SANS_LIGHT));
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

    @Override
    public void onNavigationItemClick(int index) {
        mainViewPager.setCurrentItem(index);
        currentFragmentPosition = index;
    }

    public int getCurrentFragmentPosition() {
        return currentFragmentPosition;
    }
}
