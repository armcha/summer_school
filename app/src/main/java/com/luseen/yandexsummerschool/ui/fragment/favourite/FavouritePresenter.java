package com.luseen.yandexsummerschool.ui.fragment.favourite;

import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.utils.Logger;
import com.luseen.yandexsummerschool.utils.RxUtil;

import io.realm.RealmResults;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Chatikyan on 04.04.2017.
 */

public class FavouritePresenter extends ApiPresenter<FavouriteContract.View>
        implements FavouriteContract.Presenter {

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

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

        compositeSubscription.add(dataManager.getFavouriteList()
                .doOnTerminate(getView()::hideLoading)
                .subscribe(favouriteList -> {
                    getView().onFavouriteResult(favouriteList);
                    if (favouriteList.size() == 0) {
                        getView().onEmptyResult();
                    }
                }, throwable -> {
                    Logger.log(throwable.getMessage());
                    getView().showError();
                }));
    }

    @Override
    public void doSearch(String input) {
        if (!isViewAttached())
            return;
        compositeSubscription.add(dataManager.getFavouritesByKeyWord(input)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getView()::onFavouriteResult));
    }

    @Override
    public void resetFavourite() {
        fetchFavourite();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtil.unsubscribe(compositeSubscription);
    }
}
