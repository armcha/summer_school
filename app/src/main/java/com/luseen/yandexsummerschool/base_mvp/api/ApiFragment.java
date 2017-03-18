package com.luseen.yandexsummerschool.base_mvp.api;


import com.luseen.yandexsummerschool.base_mvp.base.BaseFragment;

/**
 * Created by Chatikyan on 23.02.2017.
 */

public abstract class ApiFragment<V extends ApiContract.View, P extends ApiContract.Presenter<V>>
        extends BaseFragment<V, P>
        implements ApiContract.View {
}
