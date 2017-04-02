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
import com.luseen.yandexsummerschool.ui.adapter.HistoryAndFavouriteRecyclerAdapter;
import com.luseen.yandexsummerschool.ui.widget.SearchView;
import com.luseen.yandexsummerschool.utils.Logger;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends ApiFragment<HistoryContract.View, HistoryContract.Presenter>
        implements HistoryContract.View {

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.history_recycler_view)
    RecyclerView historyRecyclerView;

    private HistoryAndFavouriteRecyclerAdapter adapter;

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

    @NonNull
    @Override
    public HistoryContract.Presenter createPresenter() {
        return new HistoryPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.log("onDestroyView");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.log("onViewCreated");
    }

    @Override
    protected boolean whitButterKnife() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.log("onResume");
        presenter.updateHistory();
    }

    @Override
    public void onHistoryResult(List<History> historyList) {
        adapter = new HistoryAndFavouriteRecyclerAdapter(historyList);
        historyRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        historyRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void onHistoryUpdate(List<History> historyList) {
        adapter.updateHistoryList(historyList);
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
}
