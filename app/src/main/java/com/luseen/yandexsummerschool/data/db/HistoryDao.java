package com.luseen.yandexsummerschool.data.db;


import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.dictionary.Definition;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;
import com.luseen.yandexsummerschool.model.dictionary.DictionaryTranslation;
import com.luseen.yandexsummerschool.utils.Logger;
import com.luseen.yandexsummerschool.utils.RealmUtils;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Completable;
import rx.Observable;

public class HistoryDao {

    private static HistoryDao instance = null;

    public static HistoryDao getInstance() {
        if (instance == null) {
            instance = new HistoryDao();
        }
        return instance;
    }

    public Completable saveHistory(History history) {
        return Completable.fromEmitter(emitter -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(r -> {
                int uniqueId = RealmUtils.generateId(r, Definition.class);
                Logger.log("uniqueId " + uniqueId);
                Dictionary dictionary = history.getDictionary();
                dictionary.setIdentifier(dictionary.getOriginalText() + dictionary.getTranslatedText());
                LanguagePair pair = history.getLanguagePair();
                String pairId = pair.getSourceLanguage().getLangCode() + pair.getTargetLanguage().getLangCode();
                LanguagePair historyLanguagePair = createLanguagePair(pair);
                String historyIdentifier = dictionary.getOriginalText() + pairId;
                history.setLanguagePair(historyLanguagePair);
                history.setIdentifier(historyIdentifier);
                int orderId = RealmUtils.generateOrderId(r, History.class);
                history.setOrderId(orderId);
                for (Definition definition : dictionary.getDefinition()) {
                    definition.setId(++uniqueId);
                }
                r.copyToRealmOrUpdate(history);
                emitter.onCompleted();
            });
            realm.close();
        });
    }

    private LanguagePair createLanguagePair(LanguagePair languagePair) {
        String pairId = languagePair.getSourceLanguage().getLangCode()
                + languagePair.getTargetLanguage().getLangCode();
        LanguagePair historyLanguagePair = new LanguagePair();
        historyLanguagePair.setId(pairId);
        historyLanguagePair.setSourceLanguage(languagePair.getSourceLanguage());
        historyLanguagePair.setTargetLanguage(languagePair.getTargetLanguage());
        return historyLanguagePair;
    }

    public RealmResults<History> getFavouritesByKeyWord(String keyWord) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<History> realmQuery = getBaseQuery(keyWord)
                .equalTo(History.IS_FAVOURITE, true);
        RealmResults<History> favourites = realmQuery.findAllSorted(History.ORDER_ID, Sort.DESCENDING);
        realm.close();
        return favourites;
    }

    public RealmResults<History> getHistoriesByKeyWord(String keyWord) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<History> realmQuery = getBaseQuery(keyWord);
        RealmResults<History> histories = realmQuery.findAllSorted(History.ORDER_ID, Sort.DESCENDING);
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
                .findAllSortedAsync(History.ORDER_ID, Sort.DESCENDING)
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
                .findAllSorted(History.ORDER_ID, Sort.DESCENDING);
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
//        RealmResults<History> historyRealmResults = realm.where(History.class).findAll();
//        realm.executeTransaction(realm1 -> historyRealmResults.deleteAllFromRealm());
//        RealmResults<Definition> definitionRealmResults = realm.where(Definition.class).findAll();
//        realm.executeTransaction(realm1 -> definitionRealmResults.deleteAllFromRealm());
//        RealmResults<DictionaryTranslation> dictionaryTranslations = realm.where(DictionaryTranslation.class).findAll();
//        realm.executeTransaction(realm1 -> dictionaryTranslations.deleteAllFromRealm());

        deleteRealmResult(History.class);
        deleteRealmResult(Definition.class);
        deleteRealmResult(DictionaryTranslation.class);
        realm.close();
    }

    private <R extends RealmObject> void deleteRealmResult(Class<R> clazz) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<R> realmResults = realm.where(clazz).findAll();
        realm.executeTransaction(realm1 -> realmResults.deleteAllFromRealm());
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
