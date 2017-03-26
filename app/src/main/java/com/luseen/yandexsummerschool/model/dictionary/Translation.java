package com.luseen.yandexsummerschool.model.dictionary;

import com.google.gson.annotations.SerializedName;
import com.luseen.yandexsummerschool.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class Translation {

    @SerializedName("text")
    private String word;

    @SerializedName("pos")
    private String partOfSpeech;

    @SerializedName("gen")
    private String gen;

    @SerializedName("syn")
    private List<Synonym> synonyms;

    @SerializedName("mean")
    private List<TranslatedString> meanings;

    @SerializedName("ex")
    private List<Example> examples;

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

    public void setGen(String gen) {
        this.gen = gen;
    }

    public void setSynonyms(List<Synonym> synonyms) {
        this.synonyms = synonyms;
    }

    public void setMeanings(List<TranslatedString> meanings) {
        this.meanings = meanings;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

    public String getGen() {
        return gen == null ? StringUtils.EMPTY : gen;
    }

    public List<Synonym> getSynonyms() {
        return synonyms == null ? new ArrayList<>() : synonyms;
    }

    public List<TranslatedString> getMeanings() {
        return meanings == null ? new ArrayList<>() : meanings;
    }

    public List<Example> getExamples() {
        return examples == null ? new ArrayList<>() : examples;
    }
}
