package com.luseen.yandexsummerschool;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.luseen.yandexsummerschool.utils.AppConstants;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;

/**
 * Created by Chatikyan on 20.03.2017.
 */

public class App extends Application {

    private static final String SHARED_PREFERENCE_KEY = "shared_preference_key";
    private static App instance = new App();
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initFabric();
        initRealm();
        initSharedPreferences();
    }

    private void initFabric() {
        Fabric.with(this, new Crashlytics());
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(AppConstants.DATABASE_NAME)
                .rxFactory(new RealmObservableFactory())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private void initSharedPreferences() {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
    }

    public static App getInstance() {
        return instance;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
