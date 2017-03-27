package com.luseen.yandexsummerschool.model.dictionary;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class TranslatedString extends RealmObject{

    @PrimaryKey
    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
