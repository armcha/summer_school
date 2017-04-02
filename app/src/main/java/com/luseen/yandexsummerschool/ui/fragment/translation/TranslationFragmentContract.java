package com.luseen.yandexsummerschool.ui.fragment.translation;

import com.luseen.yandexsummerschool.base_mvp.api.ApiContract;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.Translation;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;

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

        void openChooseLanguageActivity(String languageChooseType);

        void setTranslationViewText(String text);

        void animateLanguageSwap(LanguagePair languagePair);

        void updateToolbarAndTranslationViewLanguages(LanguagePair languagePair);
    }

    interface Presenter extends ApiContract.Presenter<View> {

        void handleInputText(String inputText);

        void handleToolbarClicks(int id);

        void handleActivityResult(int requestCode, int resultCode);

        void clearLastInputAndTranslate();
    }
}
