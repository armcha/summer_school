package com.luseen.yandexsummerschool.data.preference;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.luseen.yandexsummerschool.App;
import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.utils.Logger;
import com.luseen.yandexsummerschool.utils.StringUtils;

/**
 * Created by Chatikyan on 27.03.2017.
 */

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String LAST_TYPED_TEXT_KEY = "last_typed_text_key";

    private final SharedPreferences preferences = App.getInstance().getSharedPreferences();
    private final SharedPreferences.Editor editor = preferences.edit();

    @Override
    public String getLastTypedText() {
        return preferences.getString(LAST_TYPED_TEXT_KEY, StringUtils.EMPTY);
    }

    @Override
    public void setLastTypedText(String lastTypedText) {
        editor.putString(LAST_TYPED_TEXT_KEY, lastTypedText);
        editor.commit();
    }
}
