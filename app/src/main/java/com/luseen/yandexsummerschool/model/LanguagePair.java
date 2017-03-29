package com.luseen.yandexsummerschool.model;

/**
 * Created by Chatikyan on 28.03.2017.
 */

public class LanguagePair {

    private Language sourceLanguage;
    private Language targetLanguage;
    private String languageChooseType;

    public LanguagePair() {
    }

    public LanguagePair(Language sourceLanguage,
                        Language targetLanguage) {
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
    }

    public Language getSourceLanguage() {
        return sourceLanguage;
    }

    public Language getTargetLanguage() {
        return targetLanguage;
    }

    public String getLanguageChooseType() {
        return languageChooseType;
    }

    public void setLanguageChooseType(String languageChooseType) {
        this.languageChooseType = languageChooseType;
    }

    public void setSourceLanguage(Language sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public void setTargetLanguage(Language targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    @Override
    public String toString() {
        return "LanguagePair{" +
                " sourceLanguage=" + sourceLanguage +
                ", targetLanguage=" + targetLanguage +
                ", languageChooseType='" + languageChooseType + '\'' +
                '}';
    }
}
