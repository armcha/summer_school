package com.luseen.yandexsummerschool.data.db;

import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.model.LanguagePair;

/**
 * Created by Chatikyan on 30.03.2017.
 */

public class LanguagePairDao extends AbstractDao {

    private static final String DEFAULT_TARGET_LANGUAGE = "en";
    private static final String DEFAULT_TARGET_LANGUAGE_FULL = "English";
    private static final String DEFAULT_SOURCE_LANGUAGE = "ru";
    private static final String DEFAULT_SOURCE_LANGUAGE_FULL = "Russian";

    private static LanguagePairDao instance = null;

    public static LanguagePairDao getInstance() {
        if (instance == null) {
            instance = new LanguagePairDao();
        }
        return instance;
    }

    public void saveLanguagePair(LanguagePair languagePair) {
        save(languagePair);
    }

    public LanguagePair getLanguagePair() {
        LanguagePair languagePair = restore(LanguagePair.class);
        if (languagePair == null) {
            return getDefaultLanguagePair();
        }
        return languagePair;
    }

    private LanguagePair getDefaultLanguagePair() {
        Language targetLang = new Language(DEFAULT_SOURCE_LANGUAGE, DEFAULT_SOURCE_LANGUAGE_FULL);
        Language sourceLang = new Language(DEFAULT_TARGET_LANGUAGE, DEFAULT_TARGET_LANGUAGE_FULL);
        return new LanguagePair(sourceLang, targetLang);
    }
}
