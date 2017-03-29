package com.luseen.yandexsummerschool.data.db;


import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.model.LastUsedLanguages;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;

import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class AppDbHelper implements DbHelper {

    private DictionaryDao dictionaryDao = DictionaryDao.getInstance();
    private LastUsedLanguageDao languageDao = LastUsedLanguageDao.getInstance();

    @Override
    public void saveDictionary(Dictionary dictionary) {
        dictionaryDao.saveObject(dictionary);
    }

    @Override
    public Observable<RealmResults<Dictionary>> getDictionaryList() {
        return Observable.fromCallable(dictionaryDao::getDictionaryRealmResult);
    }

    @Override
    public Observable<Dictionary> getDictionaryByWord(String word) {
        return Observable.fromCallable(() -> dictionaryDao.getDictionaryByWord(word));
    }

    @Override
    public void saveLastLanguage(Language language, String languageChooseType) {
        languageDao.saveLastLanguage(language, languageChooseType);
    }

    @Override
    public Observable<LastUsedLanguages> getLastUsedLanguages() {
        return Observable.fromCallable(languageDao::getLastUsedLanguages);
    }
}
