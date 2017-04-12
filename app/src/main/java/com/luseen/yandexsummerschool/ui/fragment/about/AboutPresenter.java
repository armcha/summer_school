package com.luseen.yandexsummerschool.ui.fragment.about;

import com.luseen.yandexsummerschool.base_mvp.base.BasePresenter;

/**
 * Created by Chatikyan on 11.04.2017.
 */

public class AboutPresenter extends BasePresenter<AboutContract.View>
        implements AboutContract.Presenter {

    private static final int OTHER_APP = 1;
    private static final int AGREEMENT = 2;
    private static final int PRIVACY_POLICY = 3;
    private static final int MOBILIZATION = 4;


    @Override
    public void handleItemClicks(Object id) {
        if (!isViewAttached()) return;
        int tag = ((int) id);
        switch (tag) {
            case OTHER_APP:
                getView().onOtherAppsClicked();
                break;
            case AGREEMENT:
                getView().onAgreementClicked();
                break;
            case PRIVACY_POLICY:
                getView().onPolicyClicked();
                break;
            case MOBILIZATION:
                getView().onMobilizationClicked();
                break;
        }
    }
}
