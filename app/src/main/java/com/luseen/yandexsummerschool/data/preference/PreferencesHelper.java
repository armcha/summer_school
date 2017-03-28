package com.luseen.yandexsummerschool.data.preference;

import com.luseen.yandexsummerschool.model.LanguagePair;

/**
 * Created by Chatikyan on 27.03.2017.
 */

public interface PreferencesHelper {

    String getLastTypedText();

    void setLastTypedText(String lastTypedText);

    LanguagePair getLanguagePair();

    void setLanguagePair(LanguagePair languagePair);
}
