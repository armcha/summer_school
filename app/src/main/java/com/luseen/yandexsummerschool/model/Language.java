package com.luseen.yandexsummerschool.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class Language extends RealmObject {

    @PrimaryKey
    private String langCode;

    private String fullLanguageName;

    public Language() {
    }

    public Language(String langCode, String fullLanguageName) {
        this.langCode = langCode;
        this.fullLanguageName = fullLanguageName;
    }

    public String getLangCode() {
        return langCode.trim();
    }

    public String getFullLanguageName() {
        return fullLanguageName;
    }

    @Override
    public String toString() {
        return "Language{" +
                "langCode='" + langCode + '\'' +
                ", fullLanguageName='" + fullLanguageName + '\'' +
                '}';
    }
}
