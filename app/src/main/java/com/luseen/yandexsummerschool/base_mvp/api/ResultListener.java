package com.luseen.yandexsummerschool.base_mvp.api;


import com.luseen.yandexsummerschool.data.api.RequestType;

/**
 * Created by Chatikyan on 20.02.2017.
 */

public interface ResultListener {

    void onStart(RequestType requestType);

    <T> void onSuccess(RequestType requestType, T response);

    void onError(RequestType requestType, Throwable throwable);
}
