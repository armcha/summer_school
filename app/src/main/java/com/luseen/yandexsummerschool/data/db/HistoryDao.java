package com.luseen.yandexsummerschool.data.db;


import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.dictionary.Definition;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;
import com.luseen.yandexsummerschool.model.dictionary.DictionaryTranslation;
import com.luseen.yandexsummerschool.utils.RealmUtils;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Emitter;
import rx.Observable;

public class HistoryDao {

    private static HistoryDao instance = null;

    public static HistoryDao getInstance() {
        if (instance == null) {
            instance = new HistoryDao();
        }
        return instance;
    }

    public Observable<History> saveHistory(History history) {
        return Observable.fromEmitter(emitter -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(r -> {
                int historyId = RealmUtils.generateId(r, History.class);
                int definitionId = RealmUtils.generateId(r, Definition.class);
                int dictionaryTranslationId = RealmUtils.generateId(r, DictionaryTranslation.class);
                Dictionary dictionary = history.getDictionary();
                dictionary.setIdentifier(dictionary.getOriginalText() + dictionary.getTranslatedText());
                LanguagePair pair = history.getLanguagePair();
                String pairId = pair.getSourceLanguage().getLangCode() +
                        pair.getTargetLanguage().getLangCode();
                LanguagePair historyLanguagePair = new LanguagePair();
                historyLanguagePair.setId(pairId);
                historyLanguagePair.setSourceLanguage(pair.getSourceLanguage());
                historyLanguagePair.setTargetLanguage(pair.getTargetLanguage());
                history.setLanguagePair(historyLanguagePair);
                String historyIdentifier = dictionary.getOriginalText() + pairId;
                history.setIdentifier(historyIdentifier);
                history.setId(historyId);
                for (Definition definition : dictionary.getDefinition()) {
                    definition.setId(definitionId);
                    for (DictionaryTranslation dictionaryTranslation : definition.getTranslations()) {
                        dictionaryTranslation.setId(dictionaryTranslationId);
                    }
                }
                r.copyToRealmOrUpdate(history);
            }, () -> {
                emitter.onNext(history);
                emitter.onCompleted();
            }, emitter::onError);
            realm.close();
        }, Emitter.BackpressureMode.BUFFER);
    }

    public RealmResults<History> getFavouritesByKeyWord(String keyWord) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<History> realmQuery = getBaseQuery(keyWord)
                .equalTo(History.IS_FAVOURITE, true);
        RealmResults<History> favourites = realmQuery.findAllSorted(History.ID, Sort.DESCENDING);
        realm.close();
        return favourites;
    }

    public RealmResults<History> getHistoriesByKeyWord(String keyWord) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<History> realmQuery = getBaseQuery(keyWord);
        RealmResults<History> histories = realmQuery.findAllSorted(History.ID, Sort.DESCENDING);
        realm.close();
        return histories;
    }

    public Observable<History> getHistoryByIdentifier(String identifier) {
        Realm realm = Realm.getDefaultInstance();
        Observable<History> history = realm
                .where(History.class)
                .equalTo(History.IDENTIFIER, identifier)
                .findFirstAsync()
                .asObservable()
                .filter(realmObject -> realmObject.isLoaded())
                .first()
                .map(realmObject -> ((History) realmObject));
        realm.close();
        return history;
    }


    public Observable<RealmResults<History>> getHistoryList() {
        Realm realm = Realm.getDefaultInstance();
        Observable<RealmResults<History>> histories = realm
                .where(History.class)
                .findAllSortedAsync(History.ID, Sort.DESCENDING)
                .asObservable()
                .filter(RealmResults::isLoaded)// isLoaded is true when query is completed
                .first(); // Only get the first result and then complete
        realm.close();
        return histories;
    }

    public RealmResults<History> getFavouriteList() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<History> favourites = realm
                .where(History.class)
                .equalTo(History.IS_FAVOURITE, true)
                .findAllSorted(History.ID, Sort.DESCENDING);
        realm.close();
        return favourites;
    }

    public int getHistoryListSize() {
        Realm realm = Realm.getDefaultInstance();
        long count = realm
                .where(History.class)
                .count();
        realm.close();
        return (int) count;
    }

    public void clearHistoryAndFavouriteData() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<History> historyRealmResults = realm.where(History.class).findAll();
        realm.executeTransaction(realm1 -> historyRealmResults.deleteAllFromRealm());
        realm.close();
    }

    private RealmQuery<History> getBaseQuery(String keyWord) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<History> realmQuery = realm.where(History.class)
                .beginGroup()
                .contains(History.ORIGINAL_TEXT, keyWord, Case.INSENSITIVE)
                .or()
                .contains(History.TRANSLATED_TEXT, keyWord, Case.INSENSITIVE)
                .endGroup();
        realm.close();
        return realmQuery;
    }
}
