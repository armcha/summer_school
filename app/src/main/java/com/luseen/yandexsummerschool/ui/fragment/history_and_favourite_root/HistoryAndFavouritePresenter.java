package com.luseen.yandexsummerschool.ui.fragment.history_and_favourite_root;

import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestType;

/**
 * Created by Chatikyan on 05.04.2017.
 */

public class HistoryAndFavouritePresenter extends ApiPresenter<HistoryAndFavouriteContract.View>
        implements HistoryAndFavouriteContract.Presenter {

    @Override
    public void onCreate() {
        super.onCreate();
        countHistory();
    }

    @Override
    public void onStart(RequestType requestType) {
        //no-op
    }

    @Override
    public <T> void onSuccess(RequestType requestType, T response) {
        //no-op
    }

    @Override
    public void onError(RequestType requestType, Throwable throwable) {
        //no-op
    }

    @Override
    public void clearHistoryAndFavouriteData() {
        dataManager.clearHistoryAndFavouriteData();
        if (isViewAttached()) {
            getView().onHistoryAndFavouriteCleared();
            countHistory();
        }
    }

    /**
     * Showing or hiding depend on history size
     */
    @Override
    public void countHistory() {
        if (isViewAttached()) {
            int historySize = dataManager.getHistoryListSize();
            if (historySize > 0) {
                getView().showDeleteIcon();
            } else {
                getView().hideDeleteIcon();
            }
        }
    }
}
