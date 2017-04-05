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
import com.luseen.yandexsummerschool.base_mvp.api.ApiFragment;
import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.ui.adapter.HistoryAndFavouriteRecyclerAdapter;
import com.luseen.yandexsummerschool.ui.widget.SearchView;
import com.luseen.yandexsummerschool.utils.Logger;

import butterknife.BindView;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class FavouriteFragment extends ApiFragment<FavouriteContract.View, FavouriteContract.Presenter>
        implements FavouriteContract.View,
        RealmChangeListener<RealmResults<History>>,
        HistoryAndFavouriteRecyclerAdapter.AdapterItemClickListener {

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.favourite_recycler_view)
    RecyclerView favouriteRecyclerView;

    private RealmResults<History> favouriteRealmResults;
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
    public void onFavouriteResult(RealmResults<History> favouriteRealmResults) {
        this.favouriteRealmResults = favouriteRealmResults;
        adapter = new HistoryAndFavouriteRecyclerAdapter(favouriteRealmResults);
        adapter.setAdapterItemClickListener(this);
        favouriteRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        favouriteRecyclerView.setLayoutManager(manager);
        this.favouriteRealmResults.removeAllChangeListeners();
        this.favouriteRealmResults.addChangeListener(this);
    }

    @Override
    public void onEmptyResult() {
        Logger.log("EMPTY ");
    }

    @Override
    public void onChange(RealmResults<History> favouriteList) {
        if (adapter != null) {
            adapter.updateAdapterList(favouriteList);
        }
    }

    @Override
    public void onAdapterItemClick(History history) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (favouriteRealmResults != null) {
            favouriteRealmResults.removeAllChangeListeners();
        }
    }
}
