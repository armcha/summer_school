package com.luseen.yandexsummerschool.data.db;

import io.realm.RealmObject;

/**
 * Created by Chatikyan on 30.03.2017.
 */

public interface IDao {

    <T extends RealmObject> void save(T object);

    <T extends RealmObject> T restore(Class<T> clazz);
}
