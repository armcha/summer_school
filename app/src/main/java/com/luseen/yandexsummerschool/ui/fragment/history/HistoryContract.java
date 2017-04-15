package com.luseen.yandexsummerschool.ui.fragment.history;

import android.os.Bundle;

import com.luseen.yandexsummerschool.base_mvp.api.ApiContract;
import com.luseen.yandexsummerschool.model.History;

import java.util.List;

/**
 * Created by Chatikyan on 02.04.2017.
 */

public interface HistoryContract {

    interface View extends ApiContract.View {

        void onHistoryResult(List<History> historyList);

        void showLoading();

        void hideLoading();

        void onError();

        void onEmptyResult();

        void onEmptySearchResult();
    }

    interface Presenter extends ApiContract.Presenter<View> {

        void fetchHistory();

        void doSearch(String input);

        void resetHistory();

        void decideHistoryFetching(String searchText);
    }
}
