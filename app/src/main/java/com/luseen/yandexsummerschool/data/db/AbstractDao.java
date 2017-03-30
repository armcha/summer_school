package com.luseen.yandexsummerschool.data.db;


import com.luseen.yandexsummerschool.utils.RealmUtils;

import io.realm.Realm;
import io.realm.RealmObject;

public abstract class AbstractDao implements IDao {

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

    @Override
    public <T extends RealmObject> void save(T object) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
    }

    @Override
    public <T extends RealmObject> T restore(Class<T> clazz) {
        return realm.where(clazz).findFirst();
    }
}
