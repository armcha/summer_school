package com.luseen.yandexsummerschool.model;

import java.io.Serializable;
import java.util.Comparator;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class Language extends RealmObject implements Serializable {

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
        return langCode;
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

    @Override
    public int hashCode() {
        final int prime = 13 * 2 << 1;
        return prime + langCode.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Language)) {
            return false;
        }
        Language language = ((Language) obj);
        return this.langCode.equals(language.langCode);
    }

    public static Comparator<Language> languageComparator = (o1, o2) -> {
        String firstLanguage = o1.fullLanguageName;
        String secondLanguage = o2.fullLanguageName;
        return firstLanguage.compareToIgnoreCase(secondLanguage);
    };
}
