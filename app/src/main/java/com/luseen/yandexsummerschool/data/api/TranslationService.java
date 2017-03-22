package com.luseen.yandexsummerschool.data.api;

import com.luseen.yandexsummerschool.model.AvailableLanguages;
import com.luseen.yandexsummerschool.model.Translation;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface TranslationService {

    @GET("translate")
    Observable<Translation> translate(@Query("text") String text, @Query("lang") String lang);

    @GET("getLangs")
    Observable<AvailableLanguages> availableLanguages(@Query("lang") String lang);

    @GET("detect")
    Observable<Object> detectLanguage(@Query("text") String text);
}