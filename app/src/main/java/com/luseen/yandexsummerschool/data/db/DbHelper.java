package com.luseen.yandexsummerschool.data.db;

import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.LastUsedLanguages;

import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by Chatikyan on 19.03.2017.
 */

public interface DbHelper {

    void saveHistory(History history);

    Observable<History> getHistoryByIdentifier(String identifier);

    Observable<RealmResults<History>> getHistoryList();

    Observable<RealmResults<History>> getFavouriteList();

    Observable<RealmResults<History>> getHistoriesByKeyWord(String word);

    Observable<RealmResults<History>> getFavouritesByKeyWord(String word);

    void saveLastLanguage(Language language, String languageChooseType);

    Observable<LastUsedLanguages> getLastUsedLanguages();

    LanguagePair getLanguagePair();

    void saveLanguagePair(LanguagePair languagePair);

    int getHistoryListSize();

    void clearHistoryAndFavouriteData();
}
