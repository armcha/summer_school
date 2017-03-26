package com.luseen.yandexsummerschool.model.dictionary;

import com.google.gson.annotations.SerializedName;
import com.luseen.yandexsummerschool.utils.StringUtils;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class Synonym {

    @SerializedName("text")
    private String word;

    @SerializedName("pos")
    private String partOfSpeech;

    @SerializedName("gen")
    private String gen;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getGen() {
        return gen == null ? StringUtils.EMPTY : gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }
}
