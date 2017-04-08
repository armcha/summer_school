package com.luseen.yandexsummerschool.model.dictionary;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Chatikyan on 21.03.2017.
 */

public class Dictionary extends RealmObject {

    @PrimaryKey
    private String identifier;
    private String translatedText;
    private String originalText;
    private boolean isFavourite;
    @SerializedName("def")
    private RealmList<Definition> definition = new RealmList<>();

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public RealmList<Definition> getDefinition() {
        return definition;
    }

    public void setDefinition(RealmList<Definition> definition) {
        this.definition = definition;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "translatedText='" + translatedText + '\'' +
                ", originalText='" + originalText + '\'' +
                ", definition=" + definition +
                '}';
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
