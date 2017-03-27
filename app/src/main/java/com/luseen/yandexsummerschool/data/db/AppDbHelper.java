package com.luseen.yandexsummerschool.data.db;


import com.luseen.yandexsummerschool.model.dictionary.Dictionary;

import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class AppDbHelper implements DbHelper {

    private DictionaryDao dao = DictionaryDao.getInstance();

    @Override
    public void saveDictionary(Dictionary dictionary) {
        dao.saveObject(dictionary);
    }

    @Override
    public Observable<RealmResults<Dictionary>> getDictionaryList() {
        return Observable.fromCallable(dao::getDictionaryRealmResult);
    }

    @Override
    public Observable<Dictionary> getDictionaryByWord(String word) {
        return Observable.fromCallable(() -> dao.getDictionaryByWord(word));
    }
}
