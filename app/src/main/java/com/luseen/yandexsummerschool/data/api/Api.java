package com.luseen.yandexsummerschool.data.api;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.realm.RealmObject;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static final int TYPE_TRANSLATION = 0;
    public static final int TYPE_DICTIONARY = 1;

    private static Api instance = new Api();

    public static Api getInstance() {
        return instance;
    }

    private TranslationService translationService;
    private DictionaryService dictionaryService;

    private Gson getGson() {
        return new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
    }

    private OkHttpClient getHttpClient(int type) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();
            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("key", type == TYPE_DICTIONARY ?
                            ApiHelper.DICTIONARY_KEY : ApiHelper.TRANSLATION_KEY)
                    .build();

            Request request = original
                    .newBuilder()
                    .url(url)
                    .build();
            return chain.proceed(request);
        });
        builder.readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS);

        return builder.build();
    }

    private Retrofit.Builder getBaseBuilder() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    private Api() {
        Retrofit.Builder retrofit = getBaseBuilder();

        retrofit.baseUrl(ApiHelper.TRANSLATION_URL);
        retrofit.client(getHttpClient(TYPE_TRANSLATION));
        translationService = retrofit.build().create(TranslationService.class);

        retrofit.baseUrl(ApiHelper.DICTIONARY_URL);
        retrofit.client(getHttpClient(TYPE_DICTIONARY));
        dictionaryService = retrofit.build().create(DictionaryService.class);
    }

    public TranslationService getTranslationService() {
        return translationService;
    }

    public DictionaryService getDictionaryService() {
        return dictionaryService;
    }
}