package com.luseen.yandexsummerschool.data.preference;

import android.content.SharedPreferences;

import com.luseen.yandexsummerschool.App;
import com.luseen.yandexsummerschool.utils.StringUtils;

/**
 * Created by Chatikyan on 27.03.2017.
 */

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String LAST_TYPED_TEXT_KEY = "last_typed_text_key";
    private static final String LAST_TRANSLATED_TEXT_KEY = "last_translated_text_key";

    private final SharedPreferences preferences = App.getInstance().getSharedPreferences();
    private final SharedPreferences.Editor editor = preferences.edit();

    @Override
    public String getLastTypedText() {
        return preferences.getString(LAST_TYPED_TEXT_KEY, StringUtils.EMPTY);
    }

    @Override
    public void saveLastTypedText(String lastTypedText) {
        editor.putString(LAST_TYPED_TEXT_KEY, lastTypedText);
        editor.commit();
    }

    @Override
    public String getLastTranslatedText() {
        return preferences.getString(LAST_TRANSLATED_TEXT_KEY, StringUtils.EMPTY);
    }

    @Override
    public void saveLastTranslatedWord(String lastTranslatedWord) {
        editor.putString(LAST_TRANSLATED_TEXT_KEY, lastTranslatedWord);
        editor.commit();
    }
}
