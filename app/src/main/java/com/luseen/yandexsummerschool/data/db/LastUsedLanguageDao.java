package com.luseen.yandexsummerschool.data.db;

import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.model.LastUsedLanguages;
import com.luseen.yandexsummerschool.ui.activity.choose_language.LanguageChooseType;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by Chatikyan on 29.03.2017.
 */

public class LastUsedLanguageDao extends AbstractDao {

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
        LastUsedLanguages lastUsedLanguages = getLastUsedLanguages();
        realm.beginTransaction();
        RealmList<Language> lastUsedLanguageList;
        if (languageChooseType.equals(LanguageChooseType.TYPE_SOURCE)) {
            lastUsedLanguageList = lastUsedLanguages.getLastUsedSourceLanguages();
        } else {
            lastUsedLanguageList = lastUsedLanguages.getLastUsedTargetLanguages();
        }

        boolean alreadyHasThisLanguage = alreadyHasThisLanguage(lastUsedLanguageList, language);
        if (alreadyHasThisLanguage) {
            int index = getIndex(lastUsedLanguageList, language);
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
    }

    private boolean alreadyHasThisLanguage(List<Language> lastUsedLanguageList, Language targetLanguage) {
        for (Language language : lastUsedLanguageList) {
            if (language.getLangCode().equals(targetLanguage.getLangCode())) {
                return true;
            }
        }
        return false;
    }

    private int getIndex(List<Language> lastUsedLanguageList, Language targetLanguage) {
        for (int i = 0; i < lastUsedLanguageList.size(); i++) {
            if (lastUsedLanguageList.get(i).getLangCode().equals(targetLanguage.getLangCode())) {
                return i;
            }
        }
        return -1;
    }

    public LastUsedLanguages getLastUsedLanguages() {
        LastUsedLanguages lastUsedLanguages = realm.where(LastUsedLanguages.class).findFirst();
        if (lastUsedLanguages == null) {
            lastUsedLanguages = new LastUsedLanguages();
        }
        return lastUsedLanguages;
    }
}
