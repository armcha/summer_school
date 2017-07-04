package com.luseen.yandexsummerschool.ui.activity.choose_language;

import com.google.gson.internal.LinkedTreeMap;
import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.model.AvailableLanguages;
import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.LastUsedLanguages;
import com.luseen.yandexsummerschool.utils.LanguageUtils;
import com.luseen.yandexsummerschool.utils.NetworkUtils;
import com.luseen.yandexsummerschool.utils.RxUtils;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * Starting request
     * @param requestType
     */
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

        //Getting AVAILABLE_LANGUAGES result from request
        if (requestType == RequestType.AVAILABLE_LANGUAGES) {
            AvailableLanguages availableLanguages = (AvailableLanguages) response;
            Observable<LastUsedLanguages> lastUsedLanguagesObservable = dataManager.getLastUsedLanguages();
            lastUsedLanguageSubscription = lastUsedLanguagesObservable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(lastUsedLanguages -> doOnResult(lastUsedLanguages, availableLanguages));
        }
    }

    /**
     * Sending AVAILABLE_LANGUAGES, target and source languages result to activity
     *
     * @param lastUsedLanguages  Last chosen language
     * @param availableLanguages AVAILABLE_LANGUAGES request result
     */
    private void doOnResult(LastUsedLanguages lastUsedLanguages, AvailableLanguages availableLanguages) {
        LinkedTreeMap<String, String> languageMap = availableLanguages.getLanguageLinkedMap();
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
        getView().onResult(availableLanguages, lastSelectedLanguage, lastUsedLanguageList);
    }

    @Override
    public void onError(RequestType requestType, Throwable throwable) {
        if (isViewAttached()) {
            getView().showError();
            getView().hideLoading();
        }
    }

    /**
     * Converting linkedTreeMap to language list
     *
     * @param linkedTreeMap from request
     * @return the language list
     */
    private List<Language> convertLinkedTreeMapToLanguageList(LinkedTreeMap<String, String> linkedTreeMap) {
        List<Language> languageList = new ArrayList<>();
        for (String key : linkedTreeMap.keySet()) {
            String fullLanguage = linkedTreeMap.get(key);
            Language language = new Language(key, fullLanguage);
            languageList.add(language);
        }
        Collections.sort(languageList, Language.languageComparator);
        return languageList;
    }

    /**
     * Starting AVAILABLE_LANGUAGES request
     */
    @Override
    public void startAvailableLanguagesRequest() {
        if (isViewAttached()) {
            if (NetworkUtils.isNetworkAvailable()) {
                String uiLanguage = LanguageUtils.getCurrentLocal().toString();
                makeRequest(dataManager.getAvailableTranslationLanguages(uiLanguage),
                        RequestType.AVAILABLE_LANGUAGES);
            } else {
                getView().hideLoading();
                getView().showError();
            }
        }
    }

    /**
     * Handling language which user is selected
     *
     * @param language selected language
     */
    @Override
    public void handleLanguageSelection(Language language) {
        LanguagePair dbLanguagePair = dataManager.getLanguagePair();
        LanguagePair pair = new LanguagePair();
        if (isLanguageChooseTypeSource) {
            if (language.equals(dbLanguagePair.getTargetLanguage())) {
                pair.setTargetLanguage(dbLanguagePair.getSourceLanguage());
                dataManager.saveLastTypedText(dataManager.getLastTranslatedText());
            } else {
                pair.setTargetLanguage(dbLanguagePair.getTargetLanguage());
            }
            pair.setSourceLanguage(language);
        } else {
            if (language.equals(dbLanguagePair.getSourceLanguage())) {
                pair.setSourceLanguage(dbLanguagePair.getTargetLanguage());
                dataManager.saveLastTypedText(dataManager.getLastTranslatedText());
            } else {
                pair.setSourceLanguage(dbLanguagePair.getSourceLanguage());
            }
            pair.setTargetLanguage(language);
        }
        dataManager.saveLanguagePair(pair);
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

    /**
     * Retrying to make AVAILABLE_LANGUAGES request
     */
    @Override
    public void retry() {
        startAvailableLanguagesRequest();
    }

    /**
     * Just unsubscribing
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtils.unsubscribe(lastUsedLanguageSubscription);
    }
}
