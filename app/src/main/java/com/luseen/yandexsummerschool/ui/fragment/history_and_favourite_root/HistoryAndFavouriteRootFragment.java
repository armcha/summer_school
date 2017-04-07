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

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.api.ApiFragment;
import com.luseen.yandexsummerschool.ui.adapter.HistoryAndFavouritePagerAdapter;
import com.luseen.yandexsummerschool.ui.fragment.favourite.FavouriteFragment;
import com.luseen.yandexsummerschool.ui.fragment.history.HistoryFragment;
import com.luseen.yandexsummerschool.ui.fragment.history_and_favourite_base.HistoryAndFavouriteBaseFragment;
import com.luseen.yandexsummerschool.ui.widget.FontTabLayout;
import com.luseen.yandexsummerschool.utils.Logger;

import butterknife.BindView;
import butterknife.OnClick;

public class HistoryAndFavouriteRootFragment extends ApiFragment<HistoryAndFavouriteContract.View,
        HistoryAndFavouriteContract.Presenter>
        implements HistoryAndFavouriteContract.View {

    @BindView(R.id.tab_layout)
    FontTabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private HistoryAndFavouritePagerAdapter adapter;

    public static HistoryAndFavouriteRootFragment newInstance() {
        Bundle args = new Bundle();
        HistoryAndFavouriteRootFragment fragment = new HistoryAndFavouriteRootFragment();
        fragment.setArguments(args);
        return fragment;
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
        builder.setTitle("Delete");
        builder.setMessage("Really delete ?")
                .setPositiveButton("Yes", (dialog, id) -> {
                    presenter.clearHistoryAndFavouriteData();
                })
                .setNegativeButton("No", (dialog, id) ->
                        dialog.cancel());
        builder.create().show();
    }

    @Override
    public void onHistoryAndFavouriteCleared() {
        Logger.log("onHistoryAndFavouriteCleared ");
        Fragment fragmentByIndex = adapter.getFragmentByIndex(viewPager.getCurrentItem());
//        if (fragmentByIndex instanceof HistoryFragment) {
//            HistoryFragment historyFragment = ((HistoryFragment) fragmentByIndex);
//            historyFragment.onEmptyResult();
//        } else if (fragmentByIndex instanceof FavouriteFragment) {
//            FavouriteFragment favouriteFragment = ((FavouriteFragment) fragmentByIndex);
//            favouriteFragment.onEmptyResult();
//        }

        if (fragmentByIndex instanceof HistoryAndFavouriteBaseFragment) {
            HistoryAndFavouriteBaseFragment historyFragment = ((HistoryAndFavouriteBaseFragment) fragmentByIndex);
            historyFragment.onEmptyResult();
        }
    }
}
