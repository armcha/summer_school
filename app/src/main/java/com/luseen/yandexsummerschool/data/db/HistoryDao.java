package com.luseen.yandexsummerschool.data.db;


import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.dictionary.Definition;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;
import com.luseen.yandexsummerschool.model.dictionary.DictionaryTranslation;
import com.luseen.yandexsummerschool.utils.RealmUtils;

import io.realm.RealmResults;

public class HistoryDao extends AbstractDao {

    private static HistoryDao instance = null;

    public static HistoryDao getInstance() {
        if (instance == null) {
            instance = new HistoryDao();
        }
        return instance;
    }

    public void saveObject(History history) {
        realm.beginTransaction();
        int definitionId = RealmUtils.generateId(realm, Definition.class);
        int dictionaryTranslationId = RealmUtils.generateId(realm, DictionaryTranslation.class);
        Dictionary dictionary = history.getDictionary();
        LanguagePair pair = history.getLanguagePair();
        String historyIdentifier = dictionary.getOriginalText() +
                pair.getSourceLanguage().getLangCode() +
                pair.getSourceLanguage().getLangCode();
        history.setIdentifier(historyIdentifier);
        for (Definition definition : dictionary.getDefinition()) {
            definition.setId(definitionId);
            for (DictionaryTranslation dictionaryTranslation : definition.getTranslations()) {
                dictionaryTranslation.setId(dictionaryTranslationId);
            }
        }
        realm.copyToRealmOrUpdate(history);
        realm.commitTransaction();
    }

    public Dictionary getDictionaryByWord(String word) {
        return realm
                .where(Dictionary.class)
                .equalTo(Dictionary.ORIGINAL_TEXT, word.toLowerCase())
                .findFirst();
    }

//    public Observable<RealmResults<History>> getHistoryList() {
//        return realm
//                .where(History.class)
//                .findAllAsync().asObservable();
//    }

    public RealmResults<History> getHistoryList() {
        return realm
                .where(History.class)
                .findAll();
    }

    public Dictionary getDictionary() {
        return restore(Dictionary.class);
    }
}
