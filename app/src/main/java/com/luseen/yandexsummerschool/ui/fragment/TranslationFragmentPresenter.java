package com.luseen.yandexsummerschool.ui.fragment;

import android.widget.Toast;

import com.luseen.yandexsummerschool.App;
import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.model.Dictionary;
import com.luseen.yandexsummerschool.model.Translation;
import com.luseen.yandexsummerschool.utils.Logger;

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
            getView().onResult();
            if (requestType == RequestType.TRANSLATION) {
                Translation translation = ((Translation) response);
                Logger.log(translation.getText());
                Toast.makeText(App.getInstance(), translation.getText().get(0), Toast.LENGTH_SHORT).show();
            } else if (requestType == RequestType.LOOKUP) {
                Dictionary dictionary = ((Dictionary) response);
                for (Dictionary.Definition definition : dictionary.getDefinition()) {
                    for (Dictionary.Translation translation : definition.getTranslations()) {
                        Logger.log(translation.getWord());
                    }
                }

            }
        }
    }

    @Override
    public void onError(RequestType requestType, Throwable throwable) {
        if (isViewAttached()) {
            getView().hideLoading();
            getView().showError();
        }
        Logger.log(throwable.getMessage());
    }

    @Override
    public void handleInputText(String inputText) {
        makeRequest(dataManager.translation(inputText, "ru"), RequestType.TRANSLATION);
        makeRequest(dataManager.lookup("en-ru", inputText), RequestType.LOOKUP);
    }
}
