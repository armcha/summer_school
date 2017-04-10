package com.luseen.yandexsummerschool.utils;

import java.util.Locale;

/**
 * Created by Chatikyan on 10.04.2017.
 */

public class LanguageUtils {

    public static CurrentLocale getCurrentLocal() {
        String currentLocaleLanguageCode = Locale.getDefault().getLanguage();
        return CurrentLocale.getLocaleByCode(currentLocaleLanguageCode);
    }
}
