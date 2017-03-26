package com.luseen.yandexsummerschool.data.db;

import com.luseen.yandexsummerschool.model.dictionary.Definition;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;

import java.util.List;

import rx.Observable;

/**
 * Created by Chatikyan on 19.03.2017.
 */

public interface DbHelper {

    Observable<Long> saveDictionary(Dictionary dictionary);

    Observable<List<Dictionary>> getAllDictionary();

    Observable<Boolean> isDictionaryEmpty();
}
