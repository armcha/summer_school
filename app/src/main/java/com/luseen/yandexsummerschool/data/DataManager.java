package com.luseen.yandexsummerschool.data;

import com.luseen.yandexsummerschool.data.api.Api;
import com.luseen.yandexsummerschool.data.api.ApiInterface;
import com.luseen.yandexsummerschool.data.db.DbHelper;
import com.luseen.yandexsummerschool.model.AvailableLanguages;
import com.luseen.yandexsummerschool.model.Translation;

import rx.Observable;

/**
 * Created by Chatikyan on 19.03.2017.
 */

public class DataManager implements ApiInterface, DbHelper {

    private ApiInterface apiInterface = Api.getInstance().getApiService();

    @Override
    public Observable<Translation> translation(String text, String lang) {
        return apiInterface.translation(text, lang);
    }

    @Override
    public Observable<AvailableLanguages> availableLanguages(String lang) {
        return apiInterface.availableLanguages(lang);
    }
}
