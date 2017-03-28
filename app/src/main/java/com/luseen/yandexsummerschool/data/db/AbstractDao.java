package com.luseen.yandexsummerschool.data.db;


import com.luseen.yandexsummerschool.utils.RealmUtils;

import io.realm.Realm;
import io.realm.RealmObject;

public abstract class AbstractDao {

    protected Realm realm;

    public AbstractDao() {
        realm = Realm.getDefaultInstance();
    }

    protected int generateId(Class<? extends RealmObject> targetClass) {
        return RealmUtils.generateId(realm, targetClass);
    }

    public Realm getRealm() {
        return realm;
    }

    public void close() {
        realm.close();
    }
}
