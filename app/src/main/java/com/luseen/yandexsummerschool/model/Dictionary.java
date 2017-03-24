package com.luseen.yandexsummerschool.model;

import com.google.gson.annotations.SerializedName;
import com.luseen.yandexsummerschool.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chatikyan on 21.03.2017.
 */

public class Dictionary {

    private String translatedText;

    @SerializedName("def")
    private List<Definition> definition;

    public static class Definition {
        @SerializedName("text")
        private String word;

        @SerializedName("pos")
        private String partOfSpeech;

        @SerializedName("ts")
        private String transcription;

        @SerializedName("tr")
        private List<Translation> translations;

        public String getWord() {
            return word;
        }

        public String getPartOfSpeech() {
            return partOfSpeech;
        }

        public String getTranscription() {
            return transcription;
        }

        public List<Translation> getTranslations() {
            return translations;
        }
    }

    public static class Translation {
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

        public String getPartOfSpeech() {
            return partOfSpeech;
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

    public static class Synonym {
        @SerializedName("text")
        private String word;

        @SerializedName("pos")
        private String partOfSpeech;

        @SerializedName("gen")
        private String gen;

        public String getWord() {
            return word;
        }

        public String getPartOfSpeech() {
            return partOfSpeech;
        }

        public String getGen() {
            return gen == null ? StringUtils.EMPTY : gen;
        }
    }

    public static class Example {
        @SerializedName("text")
        private String word;

        @SerializedName("tr")
        private List<TranslatedString> exampleTranslations;

        public String getWord() {
            return word;
        }

        public List<TranslatedString> getExampleTranslations() {
            return exampleTranslations == null ? new ArrayList<>() : exampleTranslations;
        }
    }

    public static class TranslatedString {
        @SerializedName("text")
        private String text;

        public String getText() {
            return text;
        }
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public List<Definition> getDefinition() {
        return definition;
    }
}
