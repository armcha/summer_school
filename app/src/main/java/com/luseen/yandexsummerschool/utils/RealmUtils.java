package com.luseen.yandexsummerschool.utils;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by Chatikyan on 27.03.2017.
 */

public class RealmUtils {

    private RealmUtils() {
        throw new RuntimeException("Private constructor cannot be accessed");
    }

    public static int generateId(Realm realm, Class<? extends RealmObject> targetClass) {
        Number number = realm.where(targetClass).max("id");
        int id;
        if (number == null) {
            id = 1;
        } else {
            id = number.intValue() + 1;
        }
        return id;
    }
}
