package com.luseen.yandexsummerschool.ui.fragment.favourite;

import com.luseen.yandexsummerschool.base_mvp.api.ApiContract;
import com.luseen.yandexsummerschool.model.History;

import io.realm.RealmResults;

/**
 * Created by Chatikyan on 04.04.2017.
 */

public interface FavouriteContract {

    interface View extends ApiContract.View {

        void showLoading();

        void showError();

        void hideLoading();

        void onFavouriteResult(RealmResults<History> favouriteList);

        void onEmptyResult();
    }

    interface Presenter extends ApiContract.Presenter<View> {

        void fetchFavourite();

        void doSearch(String input);

        void resetFavourite();
    }
}
