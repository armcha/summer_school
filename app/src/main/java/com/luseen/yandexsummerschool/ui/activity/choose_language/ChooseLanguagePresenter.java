package com.luseen.yandexsummerschool.ui.activity.choose_language;

import com.google.gson.internal.LinkedTreeMap;
import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.model.AvailableLanguages;
import com.luseen.yandexsummerschool.model.Language;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class ChooseLanguagePresenter extends ApiPresenter<ChooseLanguageContract.View>
        implements ChooseLanguageContract.Presenter {

    @Override
    public void onCreate() {
        super.onCreate();
        if (isViewAttached()) {
            makeRequest(dataManager.availableLanguages(getView().requestLanguage()), RequestType.AVAILABLE_LANGUAGES);
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
        if (!isViewAttached())
            return;
        getView().hideLoading();

        if (requestType == RequestType.AVAILABLE_LANGUAGES) {
            AvailableLanguages availableLanguages = ((AvailableLanguages) response);
            LinkedTreeMap languageMap = availableLanguages.getLanguageLinkedMap();
            availableLanguages.setLanguageList(convertLinkedTreeMapToLanguageList(languageMap));
            getView().onResult(availableLanguages);
        }
    }

    @Override
    public void onError(RequestType requestType, Throwable throwable) {
        if (isViewAttached()) {
            getView().showError();
            getView().hideLoading();
        }
    }

    private List<Language> convertLinkedTreeMapToLanguageList(LinkedTreeMap linkedTreeMap) {
        List<Language> languageList = new ArrayList<>();
        String[] languages = linkedTreeMap
                .toString()
                .replaceAll("[{}]", "")
                .trim()
                .split(",");
        List<String> languageAndCodeList = Arrays.asList(languages);
        for (String languageAndCode : languageAndCodeList) {
            String[] languageAndCodes = languageAndCode.split("=");
            String languageCode = languageAndCodes[0];
            String fullLanguage = languageAndCodes[1];
            Language language = new Language(languageCode, fullLanguage);
            languageList.add(language);
        }
        return languageList;
    }
}
