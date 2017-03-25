package com.luseen.yandexsummerschool.model;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class Language {

    private String langCode;

    private String fullLanguageName;

    public Language(String langCode, String fullLanguageName) {
        this.langCode = langCode;
        this.fullLanguageName = fullLanguageName;
    }

    public String getLangCode() {
        return langCode;
    }

    public String getFullLanguageName() {
        return fullLanguageName;
    }
}
