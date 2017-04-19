package com.luseen.yandexsummerschool.ui.fragment.history;

import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.utils.ExceptionTracker;
import com.luseen.yandexsummerschool.utils.RxUtils;

import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Chatikyan on 02.04.2017.
 */

public class HistoryPresenter extends ApiPresenter<HistoryContract.View>
        implements HistoryContract.Presenter {

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

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
    public void fetchHistory() {
        if (!isViewAttached()) return;

        compositeSubscription.add(getHistory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(histories -> {
                    if (histories.size() == 0) {
                        getView().onEmptyResult();
                    } else {
                        getView().onHistoryResult(histories);
                    }
                }, ExceptionTracker::trackException));
    }

    @Override
    public void doSearch(String input) {
        if (!isViewAttached())
            return;
        compositeSubscription.add(dataManager.getHistoriesByKeyWord(input)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(histories -> {
                    getView().onHistoryResult(histories);
                    if (histories.size() == 0) {
                        getView().onEmptySearchResult();
                    }
                }));
    }

    @Override
    public void resetHistory() {
        fetchHistory();
    }

    @Override
    public void decideHistoryFetching(String searchText) {
        if (searchText.length() > 0) {
            doSearch(searchText);
        } else {
            fetchHistory();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtils.unsubscribe(compositeSubscription);
    }

    private Observable<RealmResults<History>> getHistory() {
        return dataManager.getHistoryList()
                .observeOn(AndroidSchedulers.mainThread())
                .filter(historyList -> historyList != null);
    }
}
