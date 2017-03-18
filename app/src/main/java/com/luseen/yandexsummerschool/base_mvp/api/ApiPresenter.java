package com.luseen.yandexsummerschool.base_mvp.api;

import android.support.annotation.CallSuper;

import com.luseen.yandexsummerschool.base_mvp.base.BasePresenter;
import com.luseen.yandexsummerschool.data.ApiCallMaker;

/**
 * Created by Chatikyan on 31.01.2017.
 */

public abstract class ApiPresenter<V extends ApiContract.View> extends BasePresenter<V>
        implements ResultListener {

    protected ApiCallMaker apiCallMaker;

    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();
        apiCallMaker = new ApiCallMaker();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
        apiCallMaker.release();
    }
}
