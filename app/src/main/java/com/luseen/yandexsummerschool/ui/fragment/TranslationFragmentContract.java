package com.luseen.yandexsummerschool.ui.fragment;

import com.luseen.yandexsummerschool.base_mvp.api.ApiContract;
import com.luseen.yandexsummerschool.model.Dictionary;
import com.luseen.yandexsummerschool.model.Translation;

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
    }

    interface Presenter extends ApiContract.Presenter<View> {
        void handleInputText(String inputText);
    }
}
