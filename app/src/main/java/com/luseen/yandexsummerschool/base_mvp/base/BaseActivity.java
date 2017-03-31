package com.luseen.yandexsummerschool.base_mvp.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.luseen.yandexsummerschool.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Chatikyan on 29.01.2017.
 */

public abstract class BaseActivity<V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends MvpActivity<V, P>
        implements BaseContract.View {

    protected Unbinder unbinder;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
    }

    public void setContentView(@LayoutRes int layoutResID, boolean whitButterKnife) {
        super.setContentView(layoutResID);
        if (whitButterKnife)
            unbinder = ButterKnife.bind(this);
    }

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate();
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected void enableEnterAnimation() {
        overridePendingTransition(R.anim.slide_in_start, R.anim.slide_in_finish);
    }

    protected void enableExitAnimation() {
        overridePendingTransition(R.anim.slide_out_start, R.anim.slide_out_finish);
    }
}
