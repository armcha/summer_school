package com.luseen.yandexsummerschool.ui.activity.choose_language;

import com.luseen.yandexsummerschool.base_mvp.api.ApiContract;
import com.luseen.yandexsummerschool.model.AvailableLanguages;
import com.luseen.yandexsummerschool.model.Language;

import java.util.List;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public interface ChooseLanguageContract {

    interface View extends ApiContract.View {

        void showLoading();

        void hideLoading();

        void showError();

        void onResult(AvailableLanguages availableLanguages,
                      String lastSelectedLanguage,
                      List<Language> lastUsedLanguages);

        String languageChooseType();

        void setResultOkAndFinish();

        void setResultCancel();
    }

    interface Presenter extends ApiContract.Presenter<View> {

        void startAvailableLanguagesRequest();

        void handleLanguageSelection(Language language);

        void handleBackPress();

        void retry();
    }
}
