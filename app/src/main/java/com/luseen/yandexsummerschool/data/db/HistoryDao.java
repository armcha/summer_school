package com.luseen.yandexsummerschool.data.db;


import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.dictionary.Definition;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;
import com.luseen.yandexsummerschool.model.dictionary.DictionaryTranslation;
import com.luseen.yandexsummerschool.utils.Logger;
import com.luseen.yandexsummerschool.utils.RealmUtils;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class HistoryDao {

    private static HistoryDao instance = null;

    public static HistoryDao getInstance() {
        if (instance == null) {
            instance = new HistoryDao();
        }
        return instance;
    }

    public void saveObject(History history) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(r -> {
                int historyId = RealmUtils.generateId(r, History.class);
                //int dictionaryId = RealmUtils.generateId(realm,Dictionary.class);
                int definitionId = RealmUtils.generateId(r, Definition.class);
                int dictionaryTranslationId = RealmUtils.generateId(r, DictionaryTranslation.class);
                Dictionary dictionary = history.getDictionary();
                dictionary.setIdentifier(dictionary.getOriginalText() + dictionary.getTranslatedText());
                LanguagePair pair = history.getLanguagePair();
                String pairId = pair.getSourceLanguage().getLangCode() +
                        pair.getTargetLanguage().getLangCode();
                //pair.setId(pairId);
                LanguagePair pp = new LanguagePair();
                pp.setId(pairId);
                pp.setSourceLanguage(pair.getSourceLanguage());
                pp.setTargetLanguage(pair.getTargetLanguage());
                history.setLanguagePair(pp);
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
            });

            realm.close();
        } catch (Throwable throwable) {
            Logger.log(throwable.getMessage());
        }

    }

    public Dictionary getDictionaryByWord(String word) {
        return null;
    }

    public RealmResults<History> getHistoryList() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<History> histories = realm
                .where(History.class)
                .findAllSorted(History.ID, Sort.DESCENDING);
        realm.close();
        Logger.log("getHistoryList " + histories);
        return histories;
    }

    public int getHistoryListSize() {
        Realm realm = Realm.getDefaultInstance();
        long count = realm
                .where(History.class)
                .count();
        realm.close();
        return (int) count;
    }

    public Dictionary getDictionary() {
        Realm realm = Realm.getDefaultInstance();
        Dictionary dictionary = realm.where(Dictionary.class).findFirst();
        realm.close();
        return dictionary;
    }
}
