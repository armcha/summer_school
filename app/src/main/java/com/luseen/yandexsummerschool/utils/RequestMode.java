package com.luseen.yandexsummerschool.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Chatikyan on 22.03.2017.
 */

@IntDef({RequestMode.MODE_TRANSLATION, RequestMode.MODE_DICTIONARY})
@Retention(RetentionPolicy.SOURCE)
public @interface RequestMode {
    int MODE_TRANSLATION = 0;
    int MODE_DICTIONARY = 1;
}
