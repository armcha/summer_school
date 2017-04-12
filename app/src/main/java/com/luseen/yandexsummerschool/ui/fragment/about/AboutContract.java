package com.luseen.yandexsummerschool.ui.fragment.about;

import com.luseen.yandexsummerschool.base_mvp.base.BaseContract;

/**
 * Created by Chatikyan on 11.04.2017.
 */

public interface AboutContract {

    interface View extends BaseContract.View {

        void onOtherAppsClicked();

        void onAgreementClicked();

        void onPolicyClicked();

        void onMobilizationClicked();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void handleItemClicks(Object id);
    }
}
