package com.luseen.yandexsummerschool.ui.fragment;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestMode;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.Translation;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;
import com.luseen.yandexsummerschool.ui.activity.choose_language.LanguageChooseType;
import com.luseen.yandexsummerschool.utils.Logger;
import com.luseen.yandexsummerschool.utils.StringUtils;

import rx.Observable;

/**
 * Created by Chatikyan on 20.03.2017.
 */

public class TranslationFragmentPresenter extends ApiPresenter<TranslationFragmentContract.View>
        implements TranslationFragmentContract.Presenter {

    @Override
    public void onCreate() {
        super.onCreate();
        if (isViewAttached()) {
            getView().setUpToolbar(dataManager.getLanguagePair());
        }
    }

    @Override
    public void onStart(RequestType requestType) {
        if (isViewAttached()) {
            getView().showLoading();
        }
    }

    @Override
    public <T> void onSuccess(RequestType requestType, T response) {
        if (isViewAttached()) {
            getView().hideLoading();
            if (requestType == RequestType.TRANSLATION) {
                Translation translation = ((Translation) response);
                getView().onTranslationResult(translation);
            } else if (requestType == RequestType.LOOKUP) {
                Dictionary dictionary = ((Dictionary) response);
                dataManager.saveDictionary(dictionary);
                getView().onDictionaryResult(dictionary);
            }
        }
    }

    @Override
    public void onError(RequestType requestType, Throwable throwable) {
        if (isViewAttached()) {
            getView().hideLoading();
            getView().showError();
        }
        Logger.log("onError " + throwable.getMessage());
    }

    @Override
    public void handleInputText(String inputText) {
        int requestMode = getRequestMode(inputText);
        LanguagePair pair = dataManager.getLanguagePair();
        String translationLanguage = pair.getTargetLanguage().getLangCode();
        Logger.log("AAA " + translationLanguage);
        if (requestMode == RequestMode.MODE_TRANSLATION) {
            makeRequest(dataManager.translate(inputText, translationLanguage), RequestType.TRANSLATION);
            Logger.log("TYPE_TRANSLATION");
        } else {
            Logger.log("TYPE_DICTIONARY");
            String lookUpLanguage = pair.getSourceLanguage().getLangCode() + "-" + translationLanguage;
            Logger.log("BBB " + lookUpLanguage);
            Observable<Dictionary> dictionaryObservable = dataManager.translateAndLookUp(inputText,
                    translationLanguage, lookUpLanguage);
            makeRequest(dictionaryObservable, RequestType.LOOKUP);
        }
    }

    @Override
    public void handleToolbarClicks(int id) {
        if (!isViewAttached()) return;

        switch (id) {
            case R.id.target_language_text_view:
                getView().openChooseLanguageActivity(LanguageChooseType.TYPE_TARGET);
                break;
            case R.id.source_language_text_view:
                getView().openChooseLanguageActivity(LanguageChooseType.TYPE_SOURCE);
                break;
            case 2:
                // TODO: 27.03.2017 to way switch click
                break;
        }

    }

    private int getRequestMode(String inputText) {
        if (inputText.contains(StringUtils.SPACE)) {
            return RequestMode.MODE_TRANSLATION;
        } else {
            return RequestMode.MODE_DICTIONARY;
        }
    }
}
