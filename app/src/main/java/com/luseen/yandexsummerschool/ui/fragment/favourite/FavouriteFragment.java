package com.luseen.yandexsummerschool.ui.fragment.favourite;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.model.event_bus_events.FavouriteEvent;
import com.luseen.yandexsummerschool.model.event_bus_events.HistoryEvent;
import com.luseen.yandexsummerschool.ui.adapter.HistoryAndFavouriteRecyclerAdapter;
import com.luseen.yandexsummerschool.ui.fragment.history_and_favourite_base.HistoryAndFavouriteBaseFragment;
import com.luseen.yandexsummerschool.ui.widget.InfoShowerCoordinatorLayout;
import com.luseen.yandexsummerschool.ui.widget.SearchView;
import com.luseen.yandexsummerschool.utils.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

public class FavouriteFragment extends HistoryAndFavouriteBaseFragment<FavouriteContract.View,
        FavouriteContract.Presenter>
        implements FavouriteContract.View,
        HistoryAndFavouriteRecyclerAdapter.AdapterItemClickListener,
        SearchView.SearchListener {

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.favourite_recycler_view)
    RecyclerView favouriteRecyclerView;

    @BindView(R.id.info_shower_coordinator_layout)
    InfoShowerCoordinatorLayout infoShowerCoordinatorLayout;

    private HistoryAndFavouriteRecyclerAdapter adapter;

    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
        return fragment;
    }

    @NonNull
    @Override
    public FavouriteContract.Presenter createPresenter() {
        return new FavouritePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView.setHint(getString(R.string.favourite_search_hint));
        searchView.setSearchListener(this);
        infoShowerCoordinatorLayout.setInfoIcon(R.drawable.bookmark_check);
        infoShowerCoordinatorLayout.setInfoText(getString(R.string.empty_history_and_translation));
    }

    @Override
    protected boolean whitButterKnife() {
        return true;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFavouriteResult(List<History> favouriteRealmResults) {
        setUpOrUpdateRecyclerView(favouriteRealmResults);
    }

    private void setUpOrUpdateRecyclerView(List<History> favouriteList) {
        infoShowerCoordinatorLayout.hide();
        searchView.setVisibility(View.VISIBLE);
        if (adapter == null) {
            adapter = new HistoryAndFavouriteRecyclerAdapter(favouriteList);
            adapter.setAdapterItemClickListener(this);
            favouriteRecyclerView.setAdapter(adapter);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            favouriteRecyclerView.setLayoutManager(manager);
        } else {
            adapter.updateAdapterList(favouriteList);
        }
    }

    @Override
    public void onEmptyResult() {
        searchView.setVisibility(View.GONE);
        infoShowerCoordinatorLayout.show();
    }

    @Subscribe
    public void onChange(FavouriteEvent favouriteEvent) {
        Logger.log("Favourite on Change ");
        presenter.fetchFavourite();
    }

    @Override
    public void onAdapterItemClick(History history) {

    }

    @Override
    public void onFavouriteClicked(boolean isFavourite, String identifier) {
        presenter.fetchFavourite();
        EventBus.getDefault().post(new HistoryEvent());
        EventBus.getDefault().post(new FavouriteEvent(isFavourite, identifier));
    }

    @Override
    public void onTextChange(String input) {
        presenter.doSearch(input);
    }

    @Override
    public void onResetClicked() {
        presenter.resetFavourite();
    }

    @Override
    public void onEmptyInput() {
        presenter.resetFavourite();
    }

    @Override
    public void onVisibilityChanged(boolean isOpen) {
        if (!isOpen) {
            searchView.disable();
        }
    }
}
