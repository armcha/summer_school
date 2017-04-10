package com.luseen.yandexsummerschool.ui.activity.splash;

import com.luseen.yandexsummerschool.base_mvp.base.BasePresenter;

/**
 * Created by Chatikyan on 10.04.2017.
 */

public class SplashScreenPresenter extends BasePresenter<SplashScreenContract.View>
        implements SplashScreenContract.Presenter {


    @Override
    public void prepareRootOpening() {
        if (isViewAttached()) {
            getView().openRootActivity();
        }
    }
}
