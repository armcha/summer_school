package com.luseen.yandexsummerschool.ui.activity.choose_language;

import com.google.gson.internal.LinkedTreeMap;
import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.model.AvailableLanguages;
import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.LastUsedLanguages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class ChooseLanguagePresenter extends ApiPresenter<ChooseLanguageContract.View>
        implements ChooseLanguageContract.Presenter {

    private Subscription lastUsedLanguageSubscription;
    private boolean isLanguageChooseTypeSource;

    @Override
    public void onCreate() {
        super.onCreate();
        if (isViewAttached()) {
            isLanguageChooseTypeSource = getView().languageChooseType().equals(LanguageChooseType.TYPE_SOURCE);
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

        AvailableLanguages availableLanguages = (AvailableLanguages) response;
        if (requestType == RequestType.AVAILABLE_LANGUAGES) {
            Observable<LastUsedLanguages> lastUsedLanguagesObservable = dataManager.getLastUsedLanguages();
            lastUsedLanguageSubscription = lastUsedLanguagesObservable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(lastUsedLanguages -> doOnResult(lastUsedLanguages, availableLanguages));
        }
    }

    private void doOnResult(LastUsedLanguages lastUsedLanguages, AvailableLanguages availableLanguages) {
        LinkedTreeMap languageMap = availableLanguages.getLanguageLinkedMap();
        availableLanguages.setLanguageList(convertLinkedTreeMapToLanguageList(languageMap));
        LanguagePair languagePair = dataManager.getLanguagePair();
        String lastSelectedLanguage;
        if (isLanguageChooseTypeSource) {
            lastSelectedLanguage = languagePair.getSourceLanguage().getLangCode();
        } else {
            lastSelectedLanguage = languagePair.getTargetLanguage().getLangCode();
        }
        List<Language> lastUsedLanguageList;
        if (isLanguageChooseTypeSource) {
            lastUsedLanguageList = lastUsedLanguages.getLastUsedSourceLanguages();
        } else {
            lastUsedLanguageList = lastUsedLanguages.getLastUsedTargetLanguages();
        }
        getView().onResult(availableLanguages,
                lastSelectedLanguage,
                lastUsedLanguageList);
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

    @Override
    public void startAvailableLanguagesRequest() {
        if (isViewAttached()) {
            // TODO: 28.03.2017 get user language
            makeRequest(dataManager.availableLanguages("ru"), RequestType.AVAILABLE_LANGUAGES);
        }
    }

    @Override
    public void handleLanguageSelection(Language language) {
        LanguagePair dbLanguagePair = dataManager.getLanguagePair();
        LanguagePair pair = new LanguagePair();
        if (isLanguageChooseTypeSource) {
            pair.setSourceLanguage(language);
            pair.setTargetLanguage(dbLanguagePair.getTargetLanguage());
        } else {
            pair.setTargetLanguage(language);
            pair.setSourceLanguage(dbLanguagePair.getSourceLanguage());
        }
        pair.setLanguageChooseType(dbLanguagePair.getLanguageChooseType());
        dataManager.setLanguagePair(pair);
        if (!isViewAttached())
            return;

        dataManager.saveLastLanguage(language, getView().languageChooseType());
        getView().setResultOkAndFinish();
    }

    @Override
    public void handleBackPress() {
        if (isViewAttached()) {
            getView().setResultCancel();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (lastUsedLanguageSubscription != null) {
            lastUsedLanguageSubscription.unsubscribe();
        }
    }
}
