package com.luseen.yandexsummerschool.ui.widget;

/**
 * Created by Chatikyan on 06.04.2017.
 */

public interface InfoShower {

    void show();

    void hide();

    void forceShow();

    void forceHide();

    void setInfoText(String infoText);

    void setInfoIcon(int infoIcon);
}
