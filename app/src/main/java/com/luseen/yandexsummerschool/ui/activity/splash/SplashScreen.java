package com.luseen.yandexsummerschool.ui.activity.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.base.BaseActivity;
import com.luseen.yandexsummerschool.ui.activity.root.RootActivity;
import com.luseen.yandexsummerschool.utils.RxUtils;

import java.util.concurrent.TimeUnit;

import rx.Completable;
import rx.Subscription;

public class SplashScreen extends BaseActivity<SplashScreenContract.View, SplashScreenContract.Presenter>
        implements SplashScreenContract.View {

    public static final int ROOT_ACTIVITY_START_DELAY = 1000;

    private Subscription splashSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen, false);
        splashSubscription = Completable.timer(ROOT_ACTIVITY_START_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(() -> presenter.prepareRootOpening());
    }

    @NonNull
    @Override
    public SplashScreenContract.Presenter createPresenter() {
        return new SplashScreenPresenter();
    }

    @Override
    public void openRootActivity() {
        RootActivity.start(this);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxUtils.unsubscribe(splashSubscription);
    }
}
