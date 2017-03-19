package com.luseen.yandexsummerschool.model.body_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.luseen.yandexsummerschool.data.api.ApiInterface;

/**
 * Created by Chatikyan on 19.03.2017.
 */

public class TranslationBody {

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("lang")
    @Expose
    private String lang;

    public static TranslationBody create(String text, String language) {
        TranslationBody body = new TranslationBody();
        body.setKey(ApiInterface.KEY);
        body.setText(text);
        body.setLang(language);
        return body;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
