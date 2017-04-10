package com.luseen.yandexsummerschool.ui.fragment.history_and_favourite_base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.luseen.yandexsummerschool.base_mvp.api.ApiContract;
import com.luseen.yandexsummerschool.base_mvp.api.ApiFragment;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

import org.greenrobot.eventbus.EventBus;

public abstract class HistoryAndFavouriteBaseFragment<V extends ApiContract.View, P extends ApiContract.Presenter<V>>
        extends ApiFragment<V, P> implements KeyboardVisibilityEventListener {

    private Unregistrar unregistrar;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unregistrar = KeyboardVisibilityEvent.registerEventListener(getActivity(), this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unregistrar != null) {
            unregistrar.unregister();
        }
    }

    public abstract void onEmptyResult();
}
