package com.luseen.yandexsummerschool.ui.fragment;

import com.luseen.yandexsummerschool.base_mvp.api.ApiContract;

/**
 * Created by Chatikyan on 20.03.2017.
 */

public interface TranslationFragmentContract {

    interface View extends ApiContract.View {

    }

    interface Presenter extends ApiContract.Presenter<View> {
        void handleInputText(String inputText);
    }
}
