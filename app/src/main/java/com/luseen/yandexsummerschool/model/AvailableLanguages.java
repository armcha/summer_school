package com.luseen.yandexsummerschool.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Chatikyan on 19.03.2017.
 */

public class AvailableLanguages {

    @SerializedName("dirs")
    @Expose
    private List<String> directions;

    public List<String> getDirections() {
        return directions;
    }

    public void setDirs(List<String> directions) {
        this.directions = directions;
    }
}
