package com.luseen.yandexsummerschool.ui.fragment.history;

import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.utils.RxUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
                    if (histories.size() > 0) {
                        getView().onHistoryResult(histories);
                    } else {
                        getView().onEmptyResult();
                    }
                }, throwable -> getView().onError());
    }

    @Override
    public void updateHistory() {
        historySubscription = getHistory()
                .subscribe(new Action1<List<History>>() {
                    @Override
                    public void call(List<History> historyList) {
                        if (isViewAttached()) {
                            getView().onHistoryUpdate(historyList);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtil.unsubscribe(historySubscription);
    }

    private Observable<List<History>> getHistory() {
        return dataManager.getHistoryList()
                .observeOn(AndroidSchedulers.mainThread())
                .map(histories -> {
                    List<History> reversedList = new ArrayList<>(histories);
                    Collections.reverse(reversedList);
                    return reversedList;
                });
    }
}
