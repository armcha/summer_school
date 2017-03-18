package com.luseen.yandexsummerschool.base_mvp.base;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Chatikyan on 24.02.2017.
 */

public interface BaseContract {

    interface Presenter<V extends View> extends MvpPresenter<V> {

        void onCreate();

        void onDestroy();
    }

    interface View extends MvpView {

    }
}
