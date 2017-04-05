package com.luseen.yandexsummerschool.model;

import com.luseen.yandexsummerschool.model.dictionary.Dictionary;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Chatikyan on 02.04.2017.
 */

public class History extends RealmObject {

    public static final String TRANSLATED_TEXT = "dictionary.translatedText";
    public static final String ORIGINAL_TEXT = "dictionary.originalText";
    public static final String IS_FAVOURITE = "isFavourite";
    public static final String IDENTIFIER = "identifier";
    public static final String ID = "id";

    @PrimaryKey
    private String identifier;

    private LanguagePair languagePair;
    private Dictionary dictionary;
    private boolean isFavourite = false;

    // TODO: 04.04.2017 change id -> orderId
    //need for ordering
    private int id;

    public History() {
    }

    public History(Dictionary dictionary, LanguagePair languagePair) {
        this.dictionary = dictionary;
        this.languagePair = languagePair;
    }

    public LanguagePair getLanguagePair() {
        return languagePair;
    }

    public void setLanguagePair(LanguagePair languagePair) {
        this.languagePair = languagePair;
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

    public int getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "History{" +
                "identifier='" + identifier + '\'' +
                ", languagePair=" + languagePair +
                ", dictionary=" + dictionary +
                ", isFavourite=" + isFavourite +
                ", id=" + id +
                '}';
    }
}
