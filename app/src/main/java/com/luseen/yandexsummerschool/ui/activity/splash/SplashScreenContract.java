package com.luseen.yandexsummerschool.ui.activity.splash;

import com.luseen.yandexsummerschool.base_mvp.base.BaseContract;

/**
 * Created by Chatikyan on 10.04.2017.
 */

public interface SplashScreenContract {

    interface View extends BaseContract.View {

        void openRootActivity();
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void prepareRootOpening();
    }
}
