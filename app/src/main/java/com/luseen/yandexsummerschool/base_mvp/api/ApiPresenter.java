package com.luseen.yandexsummerschool.base_mvp.api;

import android.support.annotation.CallSuper;

import com.luseen.yandexsummerschool.base_mvp.base.BasePresenter;
import com.luseen.yandexsummerschool.data.api.ApiCallMaker;
import com.luseen.yandexsummerschool.data.DataManager;
import com.luseen.yandexsummerschool.data.api.RequestType;

import rx.Observable;

/**
 * Created by Chatikyan on 31.01.2017.
 */

public abstract class ApiPresenter<V extends ApiContract.View> extends BasePresenter<V>
        implements ResultListener {

    protected ApiCallMaker apiCallMaker;
    protected DataManager dataManager;

    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();
        apiCallMaker = new ApiCallMaker();
        dataManager = new DataManager();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
        apiCallMaker.release();
    }

    protected <T> void makeRequest(Observable<T> request, RequestType requestType) {
        apiCallMaker.startRequest(request, this, requestType);
    }
}
