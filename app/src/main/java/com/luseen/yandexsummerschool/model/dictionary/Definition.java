package com.luseen.yandexsummerschool.model.dictionary;

import com.google.gson.annotations.SerializedName;
import com.luseen.yandexsummerschool.utils.StringUtils;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class Definition extends RealmObject {

    @PrimaryKey
    private int id;

    @SerializedName("text")
    private String word;

    @SerializedName("pos")
    private String partOfSpeech;

    @SerializedName("ts")
    private String transcription;

    @SerializedName("tr")
    private RealmList<DictionaryTranslation> translations = new RealmList<>();

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getTranscription() {
        return transcription != null ? transcription : StringUtils.EMPTY;
    }

    public RealmList<DictionaryTranslation> getTranslations() {
        return translations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Definition{" +
                ", word='" + word + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", transcription='" + transcription + '\'' +
                ", translations=" + translations +
                '}';
    }
}
