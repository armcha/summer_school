package com.luseen.yandexsummerschool.data.preference;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.luseen.yandexsummerschool.App;
import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.utils.StringUtils;

/**
 * Created by Chatikyan on 27.03.2017.
 */

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String LANGUAGE_PAIR_KEY = "language_pair_key";
    private static final String LAST_TYPED_TEXT_KEY = "last_typed_text_key";
    private static final String DEFAULT_TARGET_LANGUAGE = "en";
    private static final String DEFAULT_TARGET_LANGUAGE_FULL = "English";
    private static final String DEFAULT_SOURCE_LANGUAGE = "ru";
    private static final String DEFAULT_SOURCE_LANGUAGE_FULL = "Russian";

    private final SharedPreferences preferences = App.getInstance().getSharedPreferences();
    private final SharedPreferences.Editor editor = preferences.edit();

    @Override
    public String getLastTypedText() {
        return preferences.getString(LAST_TYPED_TEXT_KEY, StringUtils.EMPTY);
    }

    @Override
    public void setLastTypedText(String lastTypedText) {
        editor.putString(LAST_TYPED_TEXT_KEY, lastTypedText);
    }

    @Override
    public LanguagePair getLanguagePair() {
        Gson gson = new Gson();
        String json = preferences.getString(LANGUAGE_PAIR_KEY, getDefaultLanguagePair());
        return gson.fromJson(json, LanguagePair.class);
    }

    @Override
    public void setLanguagePair(LanguagePair languagePair) {
        Gson gson = new Gson();
        String pairJson = gson.toJson(languagePair);
        editor.putString(LANGUAGE_PAIR_KEY, pairJson);
    }

    private String getDefaultLanguagePair() {
        Language targetLang = new Language(DEFAULT_SOURCE_LANGUAGE, DEFAULT_SOURCE_LANGUAGE_FULL);
        Language sourceLang = new Language(DEFAULT_TARGET_LANGUAGE, DEFAULT_TARGET_LANGUAGE_FULL);
        LanguagePair defaultLanguagePair = new LanguagePair(sourceLang, targetLang);
        Gson gson = new Gson();
        return gson.toJson(defaultLanguagePair);
    }
}
