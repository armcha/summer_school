package com.luseen.yandexsummerschool.base_mvp.api;

import com.luseen.yandexsummerschool.base_mvp.base.BaseContract;

/**
 * Created by Chatikyan on 24.02.2017.
 */

public interface ApiContract {

    interface Presenter<V extends BaseContract.View> extends BaseContract.Presenter<V> {

    }

    interface View extends BaseContract.View {

    }
}
