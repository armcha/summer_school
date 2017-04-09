package com.luseen.yandexsummerschool.ui.fragment.history_and_favourite_root;

import com.luseen.yandexsummerschool.base_mvp.api.ApiContract;

/**
 * Created by Chatikyan on 05.04.2017.
 */

public interface HistoryAndFavouriteContract {

    interface View extends ApiContract.View {

        void onHistoryAndFavouriteCleared();

        void showDeleteIcon();

        void hideDeleteIcon();
    }

    interface Presenter extends ApiContract.Presenter<View> {

        void clearHistoryAndFavouriteData();

        void countHistory();
    }
}
