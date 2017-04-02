package com.luseen.yandexsummerschool.ui.fragment.history;

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

        void onHistoryUpdate(List<History> historyList);
    }

    interface Presenter extends ApiContract.Presenter<View> {

        void fetchHistory();

        void updateHistory();
    }
}
