package com.luseen.yandexsummerschool.ui.fragment.history_and_favourite_root;

import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestType;

/**
 * Created by Chatikyan on 05.04.2017.
 */

public class HistoryAndFavouritePresenter extends ApiPresenter<HistoryAndFavouriteContract.View>
        implements HistoryAndFavouriteContract.Presenter {

    @Override
    public void onStart(RequestType requestType) {

    }

    @Override
    public <T> void onSuccess(RequestType requestType, T response) {

    }

    @Override
    public void onError(RequestType requestType, Throwable throwable) {

    }


    @Override
    public void clearHistoryAndFavouriteData() {
        dataManager.clearHistoryAndFavouriteData();
        if(isViewAttached()){
            getView().onHistoryAndFavouriteCleared();
        }
    }
}
