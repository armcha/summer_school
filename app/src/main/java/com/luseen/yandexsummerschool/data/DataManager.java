package com.luseen.yandexsummerschool.data;

import com.luseen.yandexsummerschool.data.api.Api;
import com.luseen.yandexsummerschool.data.api.ApiService;
import com.luseen.yandexsummerschool.data.api.DictionaryService;
import com.luseen.yandexsummerschool.data.api.TranslationService;
import com.luseen.yandexsummerschool.data.db.AppDbHelper;
import com.luseen.yandexsummerschool.data.db.DbHelper;
import com.luseen.yandexsummerschool.model.AvailableLanguages;
import com.luseen.yandexsummerschool.model.Translation;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;

import java.util.List;

import rx.Observable;

/**
 * Created by Chatikyan on 19.03.2017.
 */

public class DataManager implements ApiService{

    private TranslationService translationService = Api.getInstance().getTranslationService();
    private DictionaryService dictionaryService = Api.getInstance().getDictionaryService();
    private final AppDbHelper mDbHelper = new AppDbHelper();

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
    public Observable<Dictionary> translateAndLookUp(String text, String translationLanguage, String lookUpLanguage) {
        return Observable.zip(translate(text, translationLanguage),
                lookup(lookUpLanguage, text),
                (translation, dictionary) -> {
                    dictionary.setTranslatedText(translation.getTranslatedText());
                    return dictionary;
                });
    }

//    @Override
//    public Observable<Long> saveDictionary(Dictionary dictionary) {
//        return mDbHelper.saveDictionary(dictionary);
//    }
//
//    @Override
//    public Observable<List<Dictionary>> getAllDictionary() {
//        return mDbHelper.getAllDictionary();
//    }
//
//    @Override
//    public Observable<Boolean> isDictionaryEmpty() {
//        return mDbHelper.isDictionaryEmpty();
//    }
}
