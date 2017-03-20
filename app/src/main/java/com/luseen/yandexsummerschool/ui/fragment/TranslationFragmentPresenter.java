package com.luseen.yandexsummerschool.ui.fragment;

import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.ApiInterface;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.model.Translation;
import com.luseen.yandexsummerschool.utils.Logger;

/**
 * Created by Chatikyan on 20.03.2017.
 */

public class TranslationFragmentPresenter extends ApiPresenter<TranslationFragmentContract.View>
        implements TranslationFragmentContract.Presenter {


    @Override
    public void onStart(RequestType requestType) {

    }

    @Override
    public <T> void onSuccess(RequestType requestType, T response) {
        if (requestType == RequestType.TRANSLATION) {
            Translation translation = ((Translation) response);
            Logger.log(translation.getText());
        }
    }

    @Override
    public void onError(RequestType requestType, Throwable throwable) {

    }

    @Override
    public void handleInputText(String inputText) {
        makeRequest(dataManager.translation(ApiInterface.KEY, inputText, "ru"), RequestType.TRANSLATION);
    }
}
