package com.luseen.yandexsummerschool.ui.fragment.about;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.base.BaseFragment;
import com.luseen.yandexsummerschool.ui.widget.FontTextView;
import com.luseen.yandexsummerschool.utils.CommonUtils;
import com.luseen.yandexsummerschool.utils.CurrentLocale;
import com.luseen.yandexsummerschool.utils.LanguageUtils;

import butterknife.BindView;

import static com.luseen.yandexsummerschool.utils.AppConstants.AGREEMENT;
import static com.luseen.yandexsummerschool.utils.AppConstants.MOBILIZATION_LINK;
import static com.luseen.yandexsummerschool.utils.AppConstants.PRIVACY_POLICY;
import static com.luseen.yandexsummerschool.utils.AppConstants.PRIVACY_POLICY_RU;
import static com.luseen.yandexsummerschool.utils.AppConstants.YANDEX_PLAY_MARKET_LINK;

public class AboutFragment extends BaseFragment<AboutContract.View, AboutContract.Presenter>
        implements AboutContract.View, View.OnClickListener {

    @BindView(R.id.items_container)
    LinearLayout itemsContainer;

    @BindView(R.id.translator_icon)
    ImageView translatorIcon;

    @BindView(R.id.translator_text_view)
    FontTextView translatorTextView;

    private boolean isRussian;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isRussian = LanguageUtils.getCurrentLocal() == CurrentLocale.RUSSIAN;
        setUpItems();
        setUpIconAndText();
    }

    private void setUpItems() {
        String[] itemNames = getResources().getStringArray(R.array.about);
        for (int i = 1; i < itemsContainer.getChildCount(); i++) {
            ViewGroup aboutItem = (ViewGroup) itemsContainer.getChildAt(i);
            boolean isLastItem = i == itemsContainer.getChildCount() - 1;
            if (isLastItem) {
                aboutItem.getChildAt(aboutItem.getChildCount() - 1).setVisibility(View.GONE);
            }
            aboutItem.setTag(i);
            aboutItem.setOnClickListener(this);
            FontTextView aboutItemText = (FontTextView) aboutItem.findViewById(R.id.about_item_text);
            aboutItemText.setText(itemNames[i - 1]);
        }
    }

    private void setUpIconAndText() {
        translatorTextView.setText(getString(R.string.translator));
        int icon = isRussian ? R.drawable.about_title_ru : R.drawable.about_title;
        translatorIcon.setImageResource(icon);
    }

    @Override
    protected boolean whitButterKnife() {
        return true;
    }

    @NonNull
    @Override
    public AboutContract.Presenter createPresenter() {
        return new AboutPresenter();
    }

    @Override
    public void onClick(View view) {
        presenter.handleItemClicks(view.getTag());
    }

    @Override
    public void onOtherAppsClicked() {
        CommonUtils.openUrlWithCustomTab(YANDEX_PLAY_MARKET_LINK, getActivity());
    }

    @Override
    public void onAgreementClicked() {
        CommonUtils.openUrlWithCustomTab(AGREEMENT, getActivity());
    }

    @Override
    public void onPolicyClicked() {
        String policyLink = isRussian ? PRIVACY_POLICY_RU : PRIVACY_POLICY;
        CommonUtils.openUrlWithCustomTab(policyLink, getActivity());
    }

    @Override
    public void onMobilizationClicked() {
        CommonUtils.openUrlWithCustomTab(MOBILIZATION_LINK, getActivity());
    }
}
