package com.luseen.yandexsummerschool.base_mvp.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.luseen.yandexsummerschool.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Chatikyan on 23.02.2017.
 */

public abstract class BaseFragment<V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends MvpFragment<V, P>
        implements BaseContract.View {

    private Unbinder unbinder;

    @CallSuper
    @Override
    public void onViewCreated(android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (whitButterKnife())
            unbinder = ButterKnife.bind(this, view);
        presenter.onCreate();
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
    
    protected void showError(){
        // TODO: 12.04.2017  
    }

    protected void enableEnterAnimation() {
        getActivity().overridePendingTransition(R.anim.slide_in_start, R.anim.slide_in_finish);
    }

    protected void enableExitAnimation() {
        getActivity().overridePendingTransition(R.anim.slide_out_start, R.anim.slide_out_finish);
    }

    protected abstract boolean whitButterKnife();
}
