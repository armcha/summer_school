package com.luseen.yandexsummerschool.base_mvp.api;


import com.luseen.yandexsummerschool.base_mvp.base.BaseActivity;

/**
 * Created by Chatikyan on 29.01.2017.
 */

public abstract class ApiActivity<V extends ApiContract.View, P extends ApiContract.Presenter<V>>
        extends BaseActivity<V, P>
        implements ApiContract.View {
}
