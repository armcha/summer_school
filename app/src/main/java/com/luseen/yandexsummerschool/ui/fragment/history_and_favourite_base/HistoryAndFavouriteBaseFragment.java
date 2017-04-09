package com.luseen.yandexsummerschool.ui.fragment.history_and_favourite_base;


import com.luseen.yandexsummerschool.base_mvp.api.ApiContract;
import com.luseen.yandexsummerschool.base_mvp.api.ApiFragment;
import com.luseen.yandexsummerschool.model.event_bus_events.HistoryEvent;

import org.greenrobot.eventbus.EventBus;

public abstract class HistoryAndFavouriteBaseFragment<V extends ApiContract.View, P extends ApiContract.Presenter<V>>
        extends ApiFragment<V, P> {

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

    public abstract void onEmptyResult();
}
