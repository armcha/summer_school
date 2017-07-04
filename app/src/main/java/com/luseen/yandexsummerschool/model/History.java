package com.luseen.yandexsummerschool.model;

import com.luseen.yandexsummerschool.model.dictionary.Dictionary;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Chatikyan on 02.04.2017.
 */

public class History extends RealmObject{

    public static final String TRANSLATED_TEXT = "dictionary.translatedText";
    public static final String ORIGINAL_TEXT = "dictionary.originalText";
    public static final String IS_FAVOURITE = "isFavourite";
    public static final String IDENTIFIER = "identifier";
    public static final String ORDER_ID = "orderId";

    @PrimaryKey
    private String identifier;

    private LanguagePair languagePair;
    private Dictionary dictionary;
    private boolean isFavourite = false;

    //need for ordering
    private int orderId;

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

    public int getOrderId() {
        return orderId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "History{" +
                "identifier='" + identifier + '\'' +
                ", languagePair=" + languagePair +
                ", dictionary=" + dictionary +
                ", isFavourite=" + isFavourite +
                ", orderId=" + orderId +
                '}';
    }
}
