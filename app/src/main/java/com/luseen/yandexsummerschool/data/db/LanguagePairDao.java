package com.luseen.yandexsummerschool.data.db;

import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.utils.ExceptionTracker;
import com.luseen.yandexsummerschool.utils.Logger;

import io.realm.Realm;

/**
 * Created by Chatikyan on 30.03.2017.
 */

public class LanguagePairDao {

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
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(languagePair);
        realm.commitTransaction();
        realm.close();
    }

    public LanguagePair getLanguagePair() {
        try {
            Realm realm = Realm.getDefaultInstance();
            LanguagePair languagePair = realm.where(LanguagePair.class)
                    .equalTo(LanguagePair.ID, LanguagePair.SINGLE_ID)
                    .findFirst();
            // FIXME: 07.04.2017
            if (languagePair != null) {
                //realm.close();
                return languagePair;
            } else {
                //realm.close();
                return getDefaultLanguagePair();
            }
        } catch (Throwable throwable) {
            ExceptionTracker.trackException(throwable);
            Logger.log("Failed to save pair " + throwable.getMessage());
        }
        return getDefaultLanguagePair();
    }

    private LanguagePair getDefaultLanguagePair() {
        Language targetLang = new Language(DEFAULT_SOURCE_LANGUAGE, DEFAULT_SOURCE_LANGUAGE_FULL);
        Language sourceLang = new Language(DEFAULT_TARGET_LANGUAGE, DEFAULT_TARGET_LANGUAGE_FULL);
        return new LanguagePair(sourceLang, targetLang);
    }
}
