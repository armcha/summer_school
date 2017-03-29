package com.luseen.yandexsummerschool.model;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Chatikyan on 29.03.2017.
 */

public class LastUsedLanguages extends RealmObject {

    @PrimaryKey
    private int id = 1;

    private RealmList<Language> lastUsedSourceLanguages = new RealmList<>();

    private RealmList<Language> lastUsedTargetLanguages = new RealmList<>();

    public RealmList<Language> getLastUsedSourceLanguages() {
        return lastUsedSourceLanguages;
    }

    public void setLastUsedSourceLanguages(RealmList<Language> lastUsedSourceLanguages) {
        this.lastUsedSourceLanguages = lastUsedSourceLanguages;
    }

    public RealmList<Language> getLastUsedTargetLanguages() {
        return lastUsedTargetLanguages;
    }

    public void setLastUsedTargetLanguages(RealmList<Language> lastUsedTargetLanguages) {
        this.lastUsedTargetLanguages = lastUsedTargetLanguages;
    }

    @Override
    public String toString() {
        return "LastUsedLanguages{" +
                "id=" + id +
                ", lastUsedSourceLanguages=" + lastUsedSourceLanguages +
                ", lastUsedTargetLanguages=" + lastUsedTargetLanguages +
                '}';
    }
}
