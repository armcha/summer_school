package com.luseen.yandexsummerschool.data.db;

import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.model.LastUsedLanguages;
import com.luseen.yandexsummerschool.ui.activity.choose_language.LanguageChooseType;
import com.luseen.yandexsummerschool.utils.ExceptionTracker;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Chatikyan on 29.03.2017.
 */

public class LastUsedLanguageDao {

    private static final int MAX_LAST_USED_SIZE = 3;
    private static final int FIRST_INDEX = 0;

    private static LastUsedLanguageDao instance = null;

    public static LastUsedLanguageDao getInstance() {
        if (instance == null) {
            instance = new LastUsedLanguageDao();
        }
        return instance;
    }

    public void saveLastLanguage(Language language, String languageChooseType) {
        try {
            Realm realm = Realm.getDefaultInstance();
            LastUsedLanguages lastUsedLanguages = getLastUsedLanguages();
            realm.beginTransaction();
            RealmList<Language> lastUsedLanguageList;
            if (languageChooseType.equals(LanguageChooseType.TYPE_SOURCE)) {
                lastUsedLanguageList = lastUsedLanguages.getLastUsedSourceLanguages();
            } else {
                lastUsedLanguageList = lastUsedLanguages.getLastUsedTargetLanguages();
            }

            boolean alreadyHasThisLanguage = lastUsedLanguageList.contains(language);
            if (alreadyHasThisLanguage) {
                int index = lastUsedLanguageList.indexOf(language);
                if (index != 0) {
                    lastUsedLanguageList.remove(index);
                    lastUsedLanguageList.add(FIRST_INDEX, language);
                }
            } else {
                if (lastUsedLanguageList.size() >= MAX_LAST_USED_SIZE) {
                    lastUsedLanguageList.remove(MAX_LAST_USED_SIZE - 1);
                }
                lastUsedLanguageList.add(FIRST_INDEX, language);
            }

            realm.copyToRealmOrUpdate(lastUsedLanguages);
            realm.commitTransaction();
            realm.close();
        } catch (Exception exception) {
            ExceptionTracker.trackException(exception);
        }
    }

    public LastUsedLanguages getLastUsedLanguages() {
        Realm realm = Realm.getDefaultInstance();
        LastUsedLanguages lastUsedLanguages = realm.where(LastUsedLanguages.class).findFirst();
        if (lastUsedLanguages == null) {
            lastUsedLanguages = new LastUsedLanguages();
        }
        realm.close();
        return lastUsedLanguages;
    }
}
