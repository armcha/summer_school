package com.luseen.yandexsummerschool.ui.fragment.history;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.api.ApiFragment;
import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.model.event_bus_events.HistoryEvent;
import com.luseen.yandexsummerschool.ui.adapter.HistoryAndFavouriteRecyclerAdapter;
import com.luseen.yandexsummerschool.ui.widget.SearchView;
import com.luseen.yandexsummerschool.utils.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends ApiFragment<HistoryContract.View, HistoryContract.Presenter>
        implements HistoryContract.View,
        RealmChangeListener<RealmResults<History>>,
        HistoryAndFavouriteRecyclerAdapter.AdapterItemClickListener {

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.history_recycler_view)
    RecyclerView historyRecyclerView;

    private HistoryAndFavouriteRecyclerAdapter adapter;
    private RealmResults<History> historyRealmResults;

    public static HistoryFragment newInstance() {
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView.setHint(getString(R.string.history_search_hint));
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
    public void onHistoryResult(RealmResults<History> historyList) {
        this.historyRealmResults = historyList;
        adapter = new HistoryAndFavouriteRecyclerAdapter(historyRealmResults);
        adapter.setAdapterItemClickListener(this);
        historyRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        historyRecyclerView.setLayoutManager(manager);
        historyRealmResults.removeAllChangeListeners();
        historyRealmResults.addChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (historyRealmResults != null) {
            historyRealmResults.removeAllChangeListeners();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onEmptyResult() {

    }

    @Override
    public void onChange(RealmResults<History> historyList) {
        if (adapter != null) {
            adapter.updateAdapterList(historyList);
        }
    }

    @Override
    public void onAdapterItemClick(History history) {
        Logger.log("onHistoryItemClick " + history);
    }
}
