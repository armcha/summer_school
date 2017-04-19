package com.luseen.yandexsummerschool.utils;

/**
 * Created by Chatikyan on 10.04.2017.
 */

public enum CurrentLocale {

    RUSSIAN("ru"),
    ENGLISH("en");

    private final String languageCode;

    CurrentLocale(String languageCode) {
        this.languageCode = languageCode;
    }

    public static CurrentLocale getLocaleByCode(String localeCode) {
        for (CurrentLocale currentLocale : values()) {
            if (localeCode.equalsIgnoreCase(currentLocale.languageCode)) {
                return currentLocale;
            }
        }
        return ENGLISH;
    }

    @Override
    public String toString() {
        return languageCode;
    }
}
