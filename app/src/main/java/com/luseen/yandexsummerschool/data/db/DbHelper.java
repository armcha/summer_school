package com.luseen.yandexsummerschool.data.db;

import com.luseen.yandexsummerschool.model.dictionary.Dictionary;

import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by Chatikyan on 19.03.2017.
 */

public interface DbHelper {

    void saveDictionary(Dictionary dictionary);

    Observable<RealmResults<Dictionary>> getDictionaryList();

    Observable<Dictionary> getDictionaryByWord(String word);
}
