package com.luseen.yandexsummerschool.model;

import com.luseen.yandexsummerschool.data.api.RequestMode;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Chatikyan on 02.04.2017.
 */

public class History extends RealmObject {

    @PrimaryKey
    private String identifier;

    private LanguagePair languagePair;
    private Dictionary dictionary;
    private boolean isFavourite = false;

    @RequestMode
    private int requestMode;

    public History() {
    }

    public History(Dictionary dictionary, LanguagePair languagePair) {
        this.dictionary = dictionary;
        this.languagePair = languagePair;
    }

    public LanguagePair getLanguagePair() {
        return languagePair;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getRequestMode() {
        return requestMode;
    }

    public void setRequestMode(int requestMode) {
        this.requestMode = requestMode;
    }
}
