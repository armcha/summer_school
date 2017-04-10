package com.luseen.yandexsummerschool.data.db;

import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.utils.CurrentLocale;
import com.luseen.yandexsummerschool.utils.ExceptionTracker;
import com.luseen.yandexsummerschool.utils.LanguageUtils;
import com.luseen.yandexsummerschool.utils.Logger;

import io.realm.Realm;

/**
 * Created by Chatikyan on 30.03.2017.
 */

public class LanguagePairDao {

    private static final String DEFAULT_TARGET_LANGUAGE = "en";
    private static final String DEFAULT_TARGET_LANGUAGE_FULL = "English";
    private static final String DEFAULT_TARGET_LANGUAGE_FULL_RU = "Английский";
    private static final String DEFAULT_SOURCE_LANGUAGE = "ru";
    private static final String DEFAULT_SOURCE_LANGUAGE_FULL = "Russian";
    private static final String DEFAULT_SOURCE_LANGUAGE_FULL_RU = "Русский";

    private static LanguagePairDao instance = null;

    public static LanguagePairDao getInstance() {
        if (instance == null) {
            instance = new LanguagePairDao();
        }
        return instance;
    }

    public void saveLanguagePair(LanguagePair languagePair) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(languagePair));
        realm.close();
    }

    public LanguagePair getLanguagePair() {
        Realm realm = Realm.getDefaultInstance();
        try {
            LanguagePair languagePair = realm.where(LanguagePair.class)
                    .equalTo(LanguagePair.ID, LanguagePair.SINGLE_ID)
                    .findFirst();
            if (languagePair != null) {
                return languagePair;
            } else {
                return getDefaultLanguagePair();
            }
        } catch (Throwable throwable) {
            ExceptionTracker.trackException(throwable);
            Logger.log("Failed to save language pair " + throwable.getMessage());
        }
        return getDefaultLanguagePair();
    }

    private LanguagePair getDefaultLanguagePair() {
        boolean isRussian = LanguageUtils.getCurrentLocal() == CurrentLocale.RUSSIAN;
        Language targetLang = new Language(DEFAULT_SOURCE_LANGUAGE,
                isRussian ? DEFAULT_SOURCE_LANGUAGE_FULL_RU : DEFAULT_SOURCE_LANGUAGE_FULL);
        Language sourceLang = new Language(DEFAULT_TARGET_LANGUAGE,
                isRussian ? DEFAULT_TARGET_LANGUAGE_FULL_RU : DEFAULT_TARGET_LANGUAGE_FULL);
        return new LanguagePair(sourceLang, targetLang);
    }
}
