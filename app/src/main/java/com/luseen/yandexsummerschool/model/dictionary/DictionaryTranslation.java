package com.luseen.yandexsummerschool.model.dictionary;

import com.google.gson.annotations.SerializedName;
import com.luseen.yandexsummerschool.utils.StringUtils;

import java.util.Collections;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class DictionaryTranslation extends RealmObject {

    private int id;

    @PrimaryKey
    @SerializedName("text")
    private String word;

    @SerializedName("pos")
    private String partOfSpeech;

    @SerializedName("gen")
    private String gen;

    @SerializedName("syn")
    private RealmList<Synonym> synonyms = new RealmList<>();

    @SerializedName("mean")
    private RealmList<TranslatedString> meanings = new RealmList<>();

    @SerializedName("ex")
    private RealmList<Example> examples = new RealmList<>();

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getGen() {
        return gen == null ? StringUtils.EMPTY : gen;
    }

    public List<Synonym> getSynonyms() {
        return synonyms == null ? Collections.emptyList() : synonyms;
    }

    public List<TranslatedString> getMeanings() {
        return meanings == null ? Collections.emptyList() : meanings;
    }

    public List<Example> getExamples() {
        return examples == null ? Collections.emptyList() : examples;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DictionaryTranslation{ word='" + word + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", gen='" + gen + '\'' +
                ", synonyms=" + synonyms +
                ", meanings=" + meanings +
                ", examples=" + examples +
                '}';
    }
}
