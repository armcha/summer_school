package com.luseen.yandexsummerschool.model.dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Chatikyan on 21.03.2017.
 */

public class Dictionary {

    private String translatedText;

    @SerializedName("def")
    private List<Definition> definition;

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public List<Definition> getDefinition() {
        return definition;
    }

    public void setDefinition(List<Definition> definition) {
        this.definition = definition;
    }
}
