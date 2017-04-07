package com.luseen.yandexsummerschool.data;

import com.luseen.yandexsummerschool.data.api.Api;
import com.luseen.yandexsummerschool.data.api.ApiHelper;
import com.luseen.yandexsummerschool.data.api.DictionaryService;
import com.luseen.yandexsummerschool.data.api.TranslationService;
import com.luseen.yandexsummerschool.data.db.AppDbHelper;
import com.luseen.yandexsummerschool.data.db.DbHelper;
import com.luseen.yandexsummerschool.data.preference.AppPreferencesHelper;
import com.luseen.yandexsummerschool.data.preference.PreferencesHelper;
import com.luseen.yandexsummerschool.model.AvailableLanguages;
import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.LastUsedLanguages;
import com.luseen.yandexsummerschool.model.Translation;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;

import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by Chatikyan on 19.03.2017.
 */

public class DataManager implements ApiHelper, DbHelper, PreferencesHelper {

    private TranslationService translationService = Api.getInstance().getTranslationService();
    private DictionaryService dictionaryService = Api.getInstance().getDictionaryService();
    private PreferencesHelper preferencesHelper = new AppPreferencesHelper();
    private DbHelper appDbHelper = new AppDbHelper();

    @Override
    public Observable<Translation> translate(String text, String translationLang) {
        return translationService.translate(text, translationLang)
                .doOnNext(translation -> translation.setOriginalText(text));
    }

    @Override
    public Observable<AvailableLanguages> getAvailableTranslationLanguages(String uiLanguage) {
        return translationService.getAvailableTranslationLanguages(uiLanguage).cache();
    }

    @Override
    public Observable<Object> detectLanguage(String text) {
        return translationService.detectLanguage(text);
    }

    @Override
    public Observable<Dictionary> lookup(String lang, String text) {
        return dictionaryService.lookup(lang, text);
    }

    @Override
    public Observable<AvailableLanguages> getAvailableDictionaryLanguages() {
        return dictionaryService.getAvailableDictionaryLanguages();
    }

    @Override
    public Observable<Dictionary> translateAndLookUp(String text,
                                                     String translationLanguage,
                                                     String lookUpLanguage) {
        return Observable.zip(translate(text, translationLanguage),
                lookup(lookUpLanguage, text),
                (translation, dictionary) -> {
                    dictionary.setOriginalText(text);
                    dictionary.setTranslatedText(translation.getTranslatedText());
                    return dictionary;
                });
    }

    @Override
    public void saveHistory(History History) {
        appDbHelper.saveHistory(History);
    }

    @Override
    public Observable<History> getHistoryByIdentifier(String identifier) {
        return appDbHelper.getHistoryByIdentifier(identifier);
    }

    @Override
    public Observable<RealmResults<History>> getHistoryList() {
        return appDbHelper.getHistoryList();
    }

    @Override
    public Observable<RealmResults<History>> getFavouriteList() {
        return appDbHelper.getFavouriteList();
    }

    @Override
    public Observable<RealmResults<History>> getHistoriesByKeyWord(String word) {
        return appDbHelper.getHistoriesByKeyWord(word);
    }

    @Override
    public Observable<RealmResults<History>> getFavouritesByKeyWord(String word) {
        return appDbHelper.getFavouritesByKeyWord(word);
    }

    @Override
    public void saveLastLanguage(Language language, String languageChooseType) {
        appDbHelper.saveLastLanguage(language, languageChooseType);
    }

    @Override
    public Observable<LastUsedLanguages> getLastUsedLanguages() {
        return appDbHelper.getLastUsedLanguages();
    }

    @Override
    public LanguagePair getLanguagePair() {
        return appDbHelper.getLanguagePair();
    }

    @Override
    public void saveLanguagePair(LanguagePair languagePair) {
        appDbHelper.saveLanguagePair(languagePair);
    }

    @Override
    public int getHistoryListSize() {
        return appDbHelper.getHistoryListSize();
    }

    @Override
    public void clearHistoryAndFavouriteData() {
        appDbHelper.clearHistoryAndFavouriteData();
    }

    @Override
    public String getLastTypedText() {
        return preferencesHelper.getLastTypedText();
    }

    @Override
    public void saveLastTypedText(String lastTypedText) {
        preferencesHelper.saveLastTypedText(lastTypedText);
    }

    @Override
    public String getLastTranslatedText() {
        return preferencesHelper.getLastTranslatedText();
    }

    @Override
    public void saveLastTranslatedWord(String lastTranslatedWord) {
        preferencesHelper.saveLastTranslatedWord(lastTranslatedWord);
    }
}
