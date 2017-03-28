package com.luseen.yandexsummerschool.ui.activity.choose_language;

import com.luseen.yandexsummerschool.base_mvp.api.ApiContract;
import com.luseen.yandexsummerschool.model.AvailableLanguages;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public interface ChooseLanguageContract {

    interface View extends ApiContract.View {

        void showLoading();

        void hideLoading();

        void showError();

        void onResult(AvailableLanguages availableLanguages);
    }

    interface Presenter extends ApiContract.Presenter<View> {

        void startAvailableLanguagesRequest();
    }
}
