package com.luseen.yandexsummerschool.ui.activity.root;

import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.model.AvailableLanguages;
import com.luseen.yandexsummerschool.model.Translation;
import com.luseen.yandexsummerschool.utils.Logger;

/**
 * Created by Chatikyan on 18.03.2017.
 */

public class RootActivityPresenter extends ApiPresenter<RootActivityContract.View>
        implements RootActivityContract.Presenter {

    @Override
    public void onStart(RequestType requestType) {
        Logger.log("onStart");
    }

    @Override
    public <T> void onSuccess(RequestType requestType, T response) {
        if (requestType == RequestType.TRANSLATION) {
            Translation translation = ((Translation) response);
            Logger.log("onSuccess " + translation.getText());
        } else if (requestType == RequestType.AVAILABLE_LANGUAGES) {
            AvailableLanguages languages = ((AvailableLanguages) response);
            Logger.log("onSuccess " + languages.getDirections().size());
        }
    }

    @Override
    public void onError(RequestType requestType, Throwable throwable) {
        Logger.log("onError " + throwable.getMessage());
    }
}
