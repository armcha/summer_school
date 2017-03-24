package com.luseen.yandexsummerschool.ui.fragment;

import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestMode;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.model.Dictionary;
import com.luseen.yandexsummerschool.model.Translation;
import com.luseen.yandexsummerschool.utils.Logger;
import com.luseen.yandexsummerschool.utils.StringUtils;

import rx.Observable;

/**
 * Created by Chatikyan on 20.03.2017.
 */

public class TranslationFragmentPresenter extends ApiPresenter<TranslationFragmentContract.View>
        implements TranslationFragmentContract.Presenter {

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
        Logger.log("onError" + throwable.getMessage());
    }

    @Override
    public void handleInputText(String inputText) {
        int requestMode = getRequestMode(inputText);
        if (requestMode == RequestMode.MODE_TRANSLATION) {
            makeRequest(dataManager.translate(inputText, "ru"), RequestType.TRANSLATION);
            Logger.log("TYPE_TRANSLATION");
        } else {
            Logger.log("TYPE_DICTIONARY");
            Observable<Dictionary> dictionaryObservable = Observable.zip(dataManager.translate(inputText, "ru"),
                    dataManager.lookup("en-ru", inputText),
                    (translation, dictionary) -> {
                        dictionary.setTranslatedText(translation.getTranslatedText());
                        return dictionary;
                    });
            makeRequest(dictionaryObservable, RequestType.LOOKUP);
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
