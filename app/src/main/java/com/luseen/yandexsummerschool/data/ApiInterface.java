package com.luseen.yandexsummerschool.data;

import com.luseen.yandexsummerschool.model.Translation;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiInterface {

    String DEV_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
    public static String KEY = "trnsl.1.1.20170318T080310Z.dfec0a8f83d7436e.e03d22123ef3dcaecd09e73372c55c90ad18ec12";

    @GET("translate")
    Observable<Translation> getTranslation(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);
}