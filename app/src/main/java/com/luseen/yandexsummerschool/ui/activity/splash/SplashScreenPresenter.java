package com.luseen.yandexsummerschool.ui.activity.splash;

import android.content.SharedPreferences;

import com.luseen.yandexsummerschool.App;
import com.luseen.yandexsummerschool.base_mvp.base.BasePresenter;

import static com.luseen.yandexsummerschool.ui.activity.intro.IntroActivity.INTRO_STATE_KEY;

/**
 * Created by Chatikyan on 10.04.2017.
 */

public class SplashScreenPresenter extends BasePresenter<SplashScreenContract.View>
        implements SplashScreenContract.Presenter {

    private final SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences();

    @Override
    public void decideOpening() {
        if (isViewAttached()) {
            boolean isIntroShown = sharedPreferences.getBoolean(INTRO_STATE_KEY, false);
            if (isIntroShown) {
                getView().openRootActivity();
            } else {
                getView().openIntroActivity();
            }
        }
    }
}
