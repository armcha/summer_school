package com.luseen.yandexsummerschool.ui.fragment.history_and_favourite_root;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.api.ApiFragment;
import com.luseen.yandexsummerschool.model.event_bus_events.HistoryEvent;
import com.luseen.yandexsummerschool.model.event_bus_events.ResetEvent;
import com.luseen.yandexsummerschool.ui.adapter.HistoryAndFavouritePagerAdapter;
import com.luseen.yandexsummerschool.ui.fragment.history_and_favourite_base.HistoryAndFavouriteBaseFragment;
import com.luseen.yandexsummerschool.ui.widget.FontTabLayout;
import com.luseen.yandexsummerschool.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

public class HistoryAndFavouriteRootFragment extends ApiFragment<HistoryAndFavouriteContract.View,
        HistoryAndFavouriteContract.Presenter>
        implements HistoryAndFavouriteContract.View {

    @BindView(R.id.tab_layout)
    FontTabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.delete_history_icon)
    ImageView deleteHistoryIcon;

    private HistoryAndFavouritePagerAdapter adapter;
    private AlertDialog alertDialog;

    public static HistoryAndFavouriteRootFragment newInstance() {
        return new HistoryAndFavouriteRootFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_and_favourite, container, false);
    }

    @NonNull
    @Override
    public HistoryAndFavouriteContract.Presenter createPresenter() {
        return new HistoryAndFavouritePresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViewPager();
    }

    private void setUpViewPager() {
        adapter = new HistoryAndFavouritePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected boolean whitButterKnife() {
        return true;
    }

    @OnClick(R.id.delete_history_icon)
    public void onViewClicked() {
        showDeleteDialog();
    }

    private void showDeleteDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        CharSequence title = StringUtils.makeColorSpanWithSize(getString(R.string.delete_history_and_fav_title),
                R.color.red, 0.8f);
        builder.setTitle(title);
        builder.setMessage(getString(R.string.delete_history_and_fav_text))
                .setPositiveButton(R.string.yes, (dialog, __) -> presenter.clearHistoryAndFavouriteData())
                .setNegativeButton(R.string.cancel, (dialog, __) -> dialog.cancel());
        alertDialog = builder.create();
        alertDialog.show();
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

    //Counting history in case of any change
    @Subscribe
    public void onHistoryChange(HistoryEvent historyEvent) {
        presenter.countHistory();
    }

    //Notifying history and favourite fragment history clear event
    @Override
    public void onHistoryAndFavouriteCleared() {
        for (int i = 0; i < adapter.getCount(); i++) {
            Fragment fragmentByIndex = adapter.getFragmentByIndex(i);
            if (fragmentByIndex instanceof HistoryAndFavouriteBaseFragment) {
                HistoryAndFavouriteBaseFragment historyFragment =
                        ((HistoryAndFavouriteBaseFragment) fragmentByIndex);
                historyFragment.onEmptyResult();
            }
            EventBus.getDefault().post(new ResetEvent());
        }
    }

    @Override
    public void showDeleteIcon() {
        deleteHistoryIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDeleteIcon() {
        deleteHistoryIcon.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}
