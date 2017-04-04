package com.luseen.yandexsummerschool.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Chatikyan on 28.03.2017.
 */

public class LanguagePair extends RealmObject {

    @PrimaryKey
    private String id = "Single";

    private Language sourceLanguage;
    private Language targetLanguage;

    public LanguagePair() {
    }

    public LanguagePair(Language sourceLanguage,
                        Language targetLanguage) {
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
    }

    public Language getSourceLanguage() {
        return sourceLanguage;
    }

    public Language getTargetLanguage() {
        return targetLanguage;
    }

    public void setSourceLanguage(Language sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public void setTargetLanguage(Language targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLookupLanguage() {
        return sourceLanguage.getLangCode() + "-" + targetLanguage.getLangCode();
    }

    @Override
    public String toString() {
        return "LanguagePair{" +
                "id='" + id + '\'' +
                ", sourceLanguage=" + sourceLanguage +
                ", targetLanguage=" + targetLanguage +
                '}';
    }
}
