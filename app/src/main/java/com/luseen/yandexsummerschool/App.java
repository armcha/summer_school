package com.luseen.yandexsummerschool;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Chatikyan on 20.03.2017.
 */

public class App extends Application {

    private static App instance = new App();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initRealm();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("yandexSummerSchool.realm")
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public static App getInstance() {
        return instance;
    }
}
