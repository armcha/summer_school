package com.luseen.yandexsummerschool.data.db;


import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class AppDbHelper  {

//    private final DictionaryDao dictionaryDao = App.getInstance().getDaoSession().getDictionaryDao();
//
//    @Override
//    public Observable<Long> saveDictionary(Dictionary dictionary) {
//        return Observable.fromCallable(new Callable<Long>() {
//            @Override
//            public Long call() throws Exception {
//                return dictionaryDao.insert(dictionary);
//            }
//        });
//    }
//
//    @Override
//    public Observable<List<Dictionary>> getAllDictionary() {
//        return Observable.fromCallable(new Callable<List<Dictionary>>() {
//            @Override
//            public List<Dictionary> call() throws Exception {
//                return dictionaryDao.loadAll();
//            }
//        });
//    }
//
//    @Override
//    public Observable<Boolean> isDictionaryEmpty() {
//        Logger.log(dictionaryDao);
//        return Observable.fromCallable(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                return !(dictionaryDao.count() > 0);
//            }
//        });
//    }
}
