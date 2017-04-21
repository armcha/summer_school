package com.luseen.yandexsummerschool.ui.widget;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Chatikyan on 20.03.2017.
 */

public abstract class AbstractTextWatcher implements TextWatcher {

    @Override
    public void afterTextChanged(Editable s) {
        //no-op
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //no-op
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //no-op
    }
}
