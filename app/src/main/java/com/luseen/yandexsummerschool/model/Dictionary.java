package com.luseen.yandexsummerschool.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Chatikyan on 21.03.2017.
 */

public class Dictionary {

    @SerializedName("def")
    private List<Definition> definition;

    public static class Definition {
        @SerializedName("text")
        private String word;

        @SerializedName("pos")
        private String partOfSpeech;

        @SerializedName("tr")
        private List<Translation> translations;

        public String getWord() {
            return word;
        }

        public String getPartOfSpeech() {
            return partOfSpeech;
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

        public List<Synonym> getSynonyms() {
            return synonyms;
        }

        public List<TranslatedString> getMeanings() {
            return meanings;
        }

        public List<Example> getExamples() {
            return examples;
        }
    }

    public static class Synonym {
        @SerializedName("text")
        private String word;
        @SerializedName("pos")
        private String partOfSpeech;

        public String getWord() {
            return word;
        }

        public String getPartOfSpeech() {
            return partOfSpeech;
        }
    }

    private static class Example {
        @SerializedName("text")
        private String word;

        @SerializedName("tr")
        private List<TranslatedString> exampleTranslations;

        public String getWord() {
            return word;
        }

        public List<TranslatedString> getExampleTranslations() {
            return exampleTranslations;
        }
    }

    private static class TranslatedString {
        @SerializedName("text")
        private String text;

        public String getText() {
            return text;
        }
    }

    public List<Definition> getDefinition() {
        return definition;
    }
}
