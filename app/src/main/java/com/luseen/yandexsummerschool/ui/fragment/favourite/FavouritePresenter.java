package com.luseen.yandexsummerschool.ui.fragment.favourite;

import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.utils.ExceptionTracker;
import com.luseen.yandexsummerschool.utils.RxUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Chatikyan on 04.04.2017.
 */

public class FavouritePresenter extends ApiPresenter<FavouriteContract.View>
        implements FavouriteContract.Presenter {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

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

        compositeSubscription.add(dataManager.getFavouriteList()
                .subscribe(favouriteList -> {
                    getView().onFavouriteResult(favouriteList);
                    if (favouriteList.size() == 0) {
                        getView().onEmptyResult();//Giving empty result to fragment
                    }
                }, ExceptionTracker::trackException));
    }

    /**
     * Searching history by user input
     *
     * @param input text
     */
    @Override
    public void doSearch(String input) {
        if (!isViewAttached())
            return;
        compositeSubscription.add(dataManager.getFavouritesByKeyWord(input)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(histories -> {
                    getView().onFavouriteResult(histories);
                    if (histories.size() == 0) {
                        getView().onEmptySearchResult();
                    }
                }));
    }

    @Override
    public void resetFavourite() {
        fetchFavourite();
    }

    /**
     * On screen change it is possible to have search input, so checking it
     *
     * @param searchText input text
     */
    @Override
    public void decideFavouriteFetching(String searchText) {
        if (searchText.length() > 0) {
            doSearch(searchText);
        } else {
            fetchFavourite();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtils.unsubscribe(compositeSubscription);
    }
}
