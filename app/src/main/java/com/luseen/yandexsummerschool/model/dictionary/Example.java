package com.luseen.yandexsummerschool.model.dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class Example {

    @SerializedName("text")
    private String word;

    @SerializedName("tr")
    private List<TranslatedString> exampleTranslations;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setExampleTranslations(List<TranslatedString> exampleTranslations) {
        this.exampleTranslations = exampleTranslations;
    }

    public List<TranslatedString> getExampleTranslations() {
        return exampleTranslations == null ? new ArrayList<>() : exampleTranslations;
    }
}
