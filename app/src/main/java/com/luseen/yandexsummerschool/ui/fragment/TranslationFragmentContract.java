package com.luseen.yandexsummerschool.ui.fragment;

import com.luseen.yandexsummerschool.base_mvp.api.ApiContract;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.Translation;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;
import com.luseen.yandexsummerschool.ui.activity.choose_language.LanguageChooseType;

/**
 * Created by Chatikyan on 20.03.2017.
 */

public interface TranslationFragmentContract {

    interface View extends ApiContract.View {

        void showLoading();

        void hideLoading();

        void showError();

        void onTranslationResult(Translation translation);

        void onDictionaryResult(Dictionary dictionary);

        void setUpToolbar(LanguagePair languagePair);

        void openChooseLanguageActivity(String languageChooseType);

//        String getTargetLanguage();
//
//        String getSourceLanguage();
    }

    interface Presenter extends ApiContract.Presenter<View> {
        void handleInputText(String inputText);

        void handleToolbarClicks(int id);
    }
}
