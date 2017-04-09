package com.luseen.yandexsummerschool.utils;

import com.luseen.yandexsummerschool.model.History;

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
        return number == null ? 1 : number.intValue() + 1;
    }

    public static int generateOrderId(Realm realm, Class<? extends RealmObject> targetClass) {
        Number number = realm.where(targetClass).max(History.ORDER_ID);
        return number == null ? 1 : number.intValue() + 1;
    }
}
