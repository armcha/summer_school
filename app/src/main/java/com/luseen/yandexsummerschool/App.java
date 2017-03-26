package com.luseen.yandexsummerschool;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Chatikyan on 20.03.2017.
 */

public class App extends Application {

    private static App instance = new App();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }
}
