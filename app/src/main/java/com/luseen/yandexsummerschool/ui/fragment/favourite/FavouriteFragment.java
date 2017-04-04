package com.luseen.yandexsummerschool.ui.fragment.favourite;


import android.os.Bundle;
import android.support.annotation.NonNull;
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

import butterknife.BindView;
import io.realm.RealmResults;

public class FavouriteFragment extends ApiFragment<FavouriteContract.View, FavouriteContract.Presenter>
        implements FavouriteContract.View {

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.favourite_recycler_view)
    RecyclerView favouriteRecyclerView;

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
    public void onFavouriteResult(RealmResults<History> favouriteList) {
        adapter = new HistoryAndFavouriteRecyclerAdapter(favouriteList);
        //adapter.setHistoryItemClickListener(this);
        favouriteRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        favouriteRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void onEmptyResult() {

    }
}
