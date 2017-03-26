package com.luseen.yandexsummerschool.data.api;

import com.luseen.yandexsummerschool.model.dictionary.Dictionary;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface DictionaryService {

    @GET("lookup")
    Observable<Dictionary> lookup(@Query("lang") String lang, @Query("text") String text);
}