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
import com.luseen.yandexsummerschool.model.LanguagePair;
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
    private DbHelper appDbHelper = new AppDbHelper();
    private PreferencesHelper preferencesHelper = new AppPreferencesHelper();


    @Override
    public Observable<Translation> translate(String text, String translationLang) {
        return translationService.translate(text, translationLang);
    }

    @Override
    public Observable<AvailableLanguages> availableLanguages(String requestLang) {
        return translationService.availableLanguages(requestLang).cache();
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
    public void saveDictionary(Dictionary dictionary) {
        appDbHelper.saveDictionary(dictionary);
    }

    @Override
    public Observable<RealmResults<Dictionary>> getDictionaryList() {
        return appDbHelper.getDictionaryList();
    }

    @Override
    public Observable<Dictionary> getDictionaryByWord(String word) {
        return appDbHelper.getDictionaryByWord(word.toLowerCase());
    }

    @Override
    public String getLastTypedText() {
        return preferencesHelper.getLastTypedText();
    }

    @Override
    public void setLastTypedText(String lastTypedText) {
        preferencesHelper.setLastTypedText(lastTypedText);
    }

    @Override
    public LanguagePair getLanguagePair() {
        return preferencesHelper.getLanguagePair();
    }

    @Override
    public void setLanguagePair(LanguagePair languagePair) {
        preferencesHelper.setLanguagePair(languagePair);
    }
}
