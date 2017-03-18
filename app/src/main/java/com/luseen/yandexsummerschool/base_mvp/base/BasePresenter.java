package com.luseen.yandexsummerschool.base_mvp.base;

import android.support.annotation.CallSuper;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by Chatikyan on 31.01.2017.
 */

public abstract class BasePresenter<V extends BaseContract.View> extends MvpBasePresenter<V> {

    @CallSuper
    public void onCreate() {
    }

    @CallSuper
    public void onDestroy() {
    }
}
