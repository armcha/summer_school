package com.luseen.yandexsummerschool.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

/**
 * Created by Chatikyan on 19.03.2017.
 */

public class AvailableLanguages {

    @SerializedName("dirs")
    @Expose
    private List<String> directions;

    @SerializedName("langs")
    @Expose
    private LinkedTreeMap languageLinkedMap;

    private List<Language> languageList;

    public List<String> getDirections() {
        return directions;
    }

    public void setDirs(List<String> directions) {
        this.directions = directions;
    }

    public List<Language> getLanguageList() {
        return languageList;
    }

    public void setLanguageList(List<Language> languageList) {
        this.languageList = languageList;
    }

    public LinkedTreeMap getLanguageLinkedMap() {
        return languageLinkedMap;
    }
}
