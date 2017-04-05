package com.luseen.yandexsummerschool.ui.fragment.favourite;

import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.utils.Logger;
import com.luseen.yandexsummerschool.utils.RxUtil;

import rx.Subscription;

/**
 * Created by Chatikyan on 04.04.2017.
 */

public class FavouritePresenter extends ApiPresenter<FavouriteContract.View>
        implements FavouriteContract.Presenter {

    private Subscription favouriteSubscription;

    @Override
    public void onCreate() {
        super.onCreate();
        fetchFavourite();
    }

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
    public void fetchFavourite() {
        if (!isViewAttached()) return;

        getView().showLoading();

        favouriteSubscription = dataManager.getFavouriteList()
                .doOnTerminate(getView()::hideLoading)
                .subscribe(favouriteList -> {
                    if (favouriteList.size() == 0) {
                        getView().onEmptyResult();
                    }
                    getView().onFavouriteResult(favouriteList);
                }, throwable -> {
                    Logger.log(throwable.getMessage());
                    getView().showError();
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtil.unsubscribe(favouriteSubscription);
    }
}
