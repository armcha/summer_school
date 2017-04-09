package com.luseen.yandexsummerschool.ui.activity.root;

import com.luseen.yandexsummerschool.base_mvp.api.ApiContract;
import com.luseen.yandexsummerschool.base_mvp.base.BaseContract;

/**
 * Created by Chatikyan on 18.03.2017.
 */

public interface RootActivityContract {

    interface View extends BaseContract.View {
    }

    interface Presenter extends BaseContract.Presenter<View> {

    }
}
