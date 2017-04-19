package com.luseen.yandexsummerschool.data.db;


import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.LastUsedLanguages;

import io.realm.RealmResults;
import rx.Completable;
import rx.Observable;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class AppDbHelper implements DbHelper {

    private final HistoryDao historyDao = HistoryDao.getInstance();
    private final LastUsedLanguageDao languageDao = LastUsedLanguageDao.getInstance();
    private final LanguagePairDao languagePairDao = LanguagePairDao.getInstance();

    @Override
    public Completable saveHistory(History history) {
        return historyDao.saveHistory(history);
    }

    @Override
    public Observable<History> setFavourite(History history) {
        return historyDao.setFavourite(history);
    }

    @Override
    public Observable<History> getHistoryByIdentifier(String identifier) {
        return historyDao.getHistoryByIdentifier(identifier);
    }

    @Override
    public Observable<RealmResults<History>> getHistoryList() {
        return historyDao.getHistoryList();
    }

    @Override
    public Observable<RealmResults<History>> getFavouriteList() {
        return historyDao.getFavouriteList();
    }

    @Override
    public Observable<RealmResults<History>> getHistoriesByKeyWord(String word) {
        return Observable.fromCallable(() -> historyDao.getHistoriesByKeyWord(word));
    }

    @Override
    public Observable<RealmResults<History>> getFavouritesByKeyWord(String word) {
        return Observable.fromCallable(() -> historyDao.getFavouritesByKeyWord(word));
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
    public void saveLanguagePair(LanguagePair languagePair) {
        languagePairDao.saveLanguagePair(languagePair);
    }

    @Override
    public int getHistoryListSize() {
        return historyDao.getHistoryListSize();
    }

    @Override
    public void clearHistoryAndFavouriteData() {
        historyDao.clearHistoryAndFavouriteData();
    }
}
