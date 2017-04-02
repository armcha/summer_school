package com.luseen.yandexsummerschool.data.db;


import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.LastUsedLanguages;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;

import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class AppDbHelper implements DbHelper {

    private HistoryDao historyDao = HistoryDao.getInstance();
    private LastUsedLanguageDao languageDao = LastUsedLanguageDao.getInstance();
    private LanguagePairDao languagePairDao = LanguagePairDao.getInstance();

    @Override
    public void saveHistory(History history) {
        historyDao.saveObject(history);
    }

    @Override
    public Observable<RealmResults<History>> getHistoryList() {
        return Observable.fromCallable(historyDao::getHistoryList);
    }

    @Override
    public Observable<Dictionary> getDictionaryByWord(String word) {
        return Observable.fromCallable(() -> historyDao.getDictionaryByWord(word));
    }

    @Override
    public void saveLastLanguage(Language language, String languageChooseType) {
        languageDao.saveLastLanguage(language, languageChooseType);
    }

    @Override
    public Observable<LastUsedLanguages> getLastUsedLanguages() {
        return Observable.fromCallable(languageDao::getLastUsedLanguages);
    }

    @Override
    public LanguagePair getLanguagePair() {
        return languagePairDao.getLanguagePair();
    }

    @Override
    public void setLanguagePair(LanguagePair languagePair) {
        languagePairDao.saveLanguagePair(languagePair);
    }
}
