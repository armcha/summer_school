package com.luseen.yandexsummerschool.ui.fragment.history;

import com.luseen.yandexsummerschool.base_mvp.api.ApiContract;
import com.luseen.yandexsummerschool.model.History;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by Chatikyan on 02.04.2017.
 */

public interface HistoryContract {

    interface View extends ApiContract.View {

        void onHistoryResult(RealmResults<History> historyList);

        void showLoading();

        void hideLoading();

        void onError();

        void onEmptyResult();
    }

    interface Presenter extends ApiContract.Presenter<View> {

        void fetchHistory();

        void doSearch(String input);

        void resetHistory();
    }
}
