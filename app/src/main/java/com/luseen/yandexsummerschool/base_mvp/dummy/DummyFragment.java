package com.luseen.yandexsummerschool.base_mvp.dummy;

import android.support.annotation.NonNull;

import com.luseen.yandexsummerschool.base_mvp.base.BaseFragment;


/**
 * Created by Chatikyan on 14.02.2017.
 */

public class DummyFragment extends BaseFragment<DummyContract.View, DummyContract.Presenter>
        implements DummyContract.View {

    @NonNull
    @Override
    public DummyContract.Presenter createPresenter() {
        return new DummyPresenter();
    }

    @Override
    protected boolean whitButterKnife() {
        return true;
    }
}
