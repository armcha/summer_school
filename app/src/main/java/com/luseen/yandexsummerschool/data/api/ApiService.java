package com.luseen.yandexsummerschool.data.api;

import com.luseen.yandexsummerschool.model.dictionary.Dictionary;

import rx.Observable;

/**
 * Created by Chatikyan on 21.03.2017.
 */

public interface ApiService extends DictionaryService, TranslationService {

    String TRANSLATION_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
    String DICTIONARY_URL = "https://dictionary.yandex.net/api/v1/dicservice.json/";
    String TRANSLATION_KEY = "trnsl.1.1.20170318T080310Z.dfec0a8f83d7436e.e03d22123ef3dcaecd09e73372c55c90ad18ec12";
    String DICTIONARY_KEY = "dict.1.1.20170321T165059Z.271270ed38d05c20.93dbc1e040aa9434a2a7b9eddf94b00370401466";

    Observable<Dictionary> translateAndLookUp(String text, String translationLanguage, String lookUpLanguage);
}
