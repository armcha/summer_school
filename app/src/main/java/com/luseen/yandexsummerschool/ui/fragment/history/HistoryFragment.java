package com.luseen.yandexsummerschool.ui.fragment.history;


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
import com.luseen.yandexsummerschool.model.event_bus_events.FromHistoryOrFavouriteEvent;
import com.luseen.yandexsummerschool.model.event_bus_events.HistoryEvent;
import com.luseen.yandexsummerschool.ui.adapter.HistoryAndFavouriteRecyclerAdapter;
import com.luseen.yandexsummerschool.ui.fragment.history_and_favourite_base.HistoryAndFavouriteBaseFragment;
import com.luseen.yandexsummerschool.ui.widget.InfoShowerCoordinatorLayout;
import com.luseen.yandexsummerschool.ui.widget.SearchView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

public class HistoryFragment extends HistoryAndFavouriteBaseFragment<HistoryContract.View,
        HistoryContract.Presenter>
        implements HistoryContract.View,
        HistoryAndFavouriteRecyclerAdapter.AdapterItemClickListener,
        SearchView.SearchListener {

    @BindView(R.id.info_shower_coordinator_layout)
    InfoShowerCoordinatorLayout infoShowerCoordinatorLayout;

    @BindView(R.id.history_recycler_view)
    RecyclerView historyRecyclerView;

    @BindView(R.id.search_view)
    SearchView searchView;

    private HistoryAndFavouriteRecyclerAdapter adapter;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpSearchView(savedInstanceState);
        infoShowerCoordinatorLayout.setInfoIcon(R.drawable.ic_no_history);
        presenter.decideHistoryFetching(searchView.getSearchText());
    }

    private void setUpSearchView(Bundle savedInstanceState) {
        searchView.setHint(getString(R.string.history_search_hint));
        searchView.setSearchListener(this);
        searchView.restoreInstance(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        searchView.onSaveInstance(outState);
    }

    @NonNull
    @Override
    public HistoryContract.Presenter createPresenter() {
        return new HistoryPresenter();
    }

    @Override
    protected boolean whitButterKnife() {
        return true;
    }

    @Override
    public void onHistoryResult(List<History> historyList) {
        setUpOrUpdateRecyclerView(historyList);
    }

    private void setUpOrUpdateRecyclerView(List<History> historyList) {
        if (historyList.size() != 0)
            infoShowerCoordinatorLayout.hide();
        searchView.setVisibility(View.VISIBLE);
        if (adapter == null) {
            adapter = new HistoryAndFavouriteRecyclerAdapter(historyList);
            adapter.setAdapterItemClickListener(this);
            historyRecyclerView.setAdapter(adapter);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            historyRecyclerView.setLayoutManager(manager);
            historyRecyclerView.setHasFixedSize(true);
        } else {
           adapter.updateAdapterList(historyList);
        }
    }

    @Override
    public void onEmptyResult() {
        searchView.setVisibility(View.GONE);
        infoShowerCoordinatorLayout.whitTopMargin(false);
        infoShowerCoordinatorLayout.setInfoText(getString(R.string.empty_history_and_translation));
        infoShowerCoordinatorLayout.show();
    }

    @Override
    public void onEmptySearchResult() {
        infoShowerCoordinatorLayout.whitTopMargin(true);
        infoShowerCoordinatorLayout.setInfoText(getString(R.string.no_matches_found));
        infoShowerCoordinatorLayout.show();
    }

    @Subscribe
    public void onChange(HistoryEvent historyEvent) {
        searchView.reset();
        presenter.fetchHistory();
    }

    @Override
    public void onAdapterItemClick(History history) {
        EventBus.getDefault().post(new FromHistoryOrFavouriteEvent(history));
    }

    @Override
    public void onFavouriteClicked(boolean isFavourite, String identifier) {
        EventBus.getDefault().post(new FavouriteEvent(isFavourite, identifier));
    }

    @Override
    public void onTextChange(String input) {
        presenter.doSearch(input);
    }

    @Override
    public void onResetClicked() {
        presenter.resetHistory();
    }

    @Override
    public void onEmptyInput() {
        presenter.resetHistory();
    }

    @Override
    public void onVisibilityChanged(boolean isOpen) {
        if (!isOpen) {
            searchView.disable();
        }
    }
}
