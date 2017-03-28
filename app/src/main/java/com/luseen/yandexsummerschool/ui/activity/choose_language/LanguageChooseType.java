package com.luseen.yandexsummerschool.ui.activity.choose_language;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Chatikyan on 27.03.2017.
 */

@StringDef({LanguageChooseType.TYPE_SOURCE, LanguageChooseType.TYPE_TARGET})
@Retention(RetentionPolicy.SOURCE)
public @interface LanguageChooseType {
    String TYPE_SOURCE = "type_source";
    String TYPE_TARGET = "type_target";
}
