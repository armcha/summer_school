package com.luseen.yandexsummerschool.ui.widget;

/**
 * Created by Chatikyan on 09.04.2017.
 */

public interface AnimatableView {

    void show();

    void hide();

    default void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
