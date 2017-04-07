package com.luseen.yandexsummerschool.ui.fragment.history_and_favourite_base;


import com.luseen.yandexsummerschool.base_mvp.api.ApiContract;
import com.luseen.yandexsummerschool.base_mvp.api.ApiFragment;

public abstract class HistoryAndFavouriteBaseFragment<V extends ApiContract.View, P extends ApiContract.Presenter<V>>
        extends ApiFragment<V, P> {

    public abstract void onEmptyResult();

}
