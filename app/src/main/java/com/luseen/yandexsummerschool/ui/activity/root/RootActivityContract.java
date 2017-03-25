package com.luseen.yandexsummerschool.ui.activity.root;

import com.luseen.yandexsummerschool.base_mvp.api.ApiContract;

/**
 * Created by Chatikyan on 18.03.2017.
 */

public interface RootActivityContract {

    interface View extends ApiContract.View {
    }

    interface Presenter extends ApiContract.Presenter<View> {

    }
}
