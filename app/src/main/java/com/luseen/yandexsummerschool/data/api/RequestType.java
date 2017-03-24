package com.luseen.yandexsummerschool.data.api;

/**
 * Created by Chatikyan on 19.03.2017.
 */

public enum RequestType {

    TRANSLATION("Translation"),
    AVAILABLE_LANGUAGES("AvailableLanguages"),
    DETECT_LANGUAGES("DetectLanguage"),
    LOOKUP("LookUp"),
    LOOKUP_AND_TRANSLATION("LookUp");

    private String requestType;

    RequestType(String requestType) {
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        return requestType;
    }
}
