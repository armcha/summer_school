package com.luseen.yandexsummerschool.model.dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class Example extends RealmObject{

    @PrimaryKey
    @SerializedName("text")
    private String word;

    @SerializedName("tr")
    private RealmList<TranslatedString> exampleTranslations;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setExampleTranslations(RealmList<TranslatedString> exampleTranslations) {
        this.exampleTranslations = exampleTranslations;
    }

    public List<TranslatedString> getExampleTranslations() {
        return exampleTranslations == null ? new ArrayList<>() : exampleTranslations;
    }
}
