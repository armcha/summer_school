package com.luseen.yandexsummerschool.ui.fragment.history;

import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.utils.Logger;
import com.luseen.yandexsummerschool.utils.RxUtil;

import io.realm.RealmResults;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Chatikyan on 02.04.2017.
 */

public class HistoryPresenter extends ApiPresenter<HistoryContract.View>
        implements HistoryContract.Presenter {

    private Subscription historySubscription;

    @Override
    public void onCreate() {
        super.onCreate();
        fetchHistory();
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
    public void fetchHistory() {
        if (!isViewAttached()) return;

        getView().showLoading();

        historySubscription = getHistory()
                .doOnTerminate(getView()::hideLoading)
                .subscribe(histories -> {
                    if (histories.size() == 0) {
                        getView().onEmptyResult();
                    }
                    getView().onHistoryResult(histories);

                }, throwable -> {
                    Logger.log(throwable.getMessage());
                    getView().onError();
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtil.unsubscribe(historySubscription);
    }

    private Observable<RealmResults<History>> getHistory() {
        return dataManager.getHistoryList()
                .observeOn(AndroidSchedulers.mainThread())
                .filter(historyList -> historyList != null);
    }
}
