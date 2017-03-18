package com.luseen.yandexsummerschool.base_mvp.dummy;


import com.luseen.yandexsummerschool.base_mvp.base.BaseContract;

/**
 * Created by Chatikyan on 24.02.2017.
 */

public interface DummyContract {

    interface Presenter extends BaseContract.Presenter<View> {
    }

    interface View extends BaseContract.View {
    }
}
