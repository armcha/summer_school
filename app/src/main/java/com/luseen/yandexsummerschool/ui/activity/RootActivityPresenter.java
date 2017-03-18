package com.luseen.yandexsummerschool.ui.activity;

import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.Api;
import com.luseen.yandexsummerschool.data.ApiInterface;

/**
 * Created by Chatikyan on 18.03.2017.
 */

public class RootActivityPresenter extends ApiPresenter<RootActivityContract.View>
        implements RootActivityContract.Presenter {

    @Override
    public void onCreate() {
        super.onCreate();
        apiCallMaker.startRequest(Api.getInstance().getTranslation(ApiInterface.KEY, "Hello", "ru"), this);
    }


}
