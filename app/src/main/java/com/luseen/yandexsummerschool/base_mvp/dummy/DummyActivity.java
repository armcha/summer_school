package com.luseen.yandexsummerschool.base_mvp.dummy;

import android.support.annotation.NonNull;

import com.luseen.yandexsummerschool.base_mvp.base.BaseActivity;


/**
 * Created by Chatikyan on 16.02.2017.
 */

public abstract class DummyActivity extends BaseActivity<DummyContract.View, DummyPresenter>
        implements DummyContract.View {

    @NonNull
    @Override
    public DummyPresenter createPresenter() {
        return new DummyPresenter();
    }
}
