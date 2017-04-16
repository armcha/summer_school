package com.luseen.yandexsummerschool.ui.activity.choose_language;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.api.ApiActivity;
import com.luseen.yandexsummerschool.model.AvailableLanguages;
import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.ui.adapter.ChooseLanguageItemSelectListener;
import com.luseen.yandexsummerschool.ui.adapter.ChooseLanguageRecyclerAdapter;
import com.luseen.yandexsummerschool.ui.widget.AnimatedTextView;
import com.luseen.yandexsummerschool.ui.widget.YaProgressView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.luseen.yandexsummerschool.utils.AppConstants.LANGUAGE_CHOOSE_TYPE_INTENT_KEY;

public class ChooseLanguageActivity extends ApiActivity<ChooseLanguageContract.View, ChooseLanguageContract.Presenter>
        implements ChooseLanguageContract.View,
        ChooseLanguageItemSelectListener {

    @BindView(R.id.choose_language_recycler_view)
    RecyclerView chooseLanguageRecyclerView;

    @BindView(R.id.toolbar_title)
    AnimatedTextView toolbarTitleTextView;

    @BindView(R.id.progress_view)
    YaProgressView progressView;

    @BindView(R.id.error_view)
    LinearLayout errorView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String languageChooseType;

    public static Intent getStartIntent(Context context,
                                        String languageChooseType) {
        Intent intent = new Intent(context, ChooseLanguageActivity.class);
        intent.putExtra(LANGUAGE_CHOOSE_TYPE_INTENT_KEY, languageChooseType);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        languageChooseType = getIntent().getStringExtra(LANGUAGE_CHOOSE_TYPE_INTENT_KEY);
        presenter.startAvailableLanguagesRequest();
        setUpToolbar();
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        String toolbarTitle;
        if (languageChooseType.equals(LanguageChooseType.TYPE_SOURCE)) {
            toolbarTitle = getString(R.string.source_language);
        } else {
            toolbarTitle = getString(R.string.target_language);
        }
        toolbarTitleTextView.setAnimatedText(toolbarTitle);
    }

    @NonNull
    @Override
    public ChooseLanguageContract.Presenter createPresenter() {
        return new ChooseLanguagePresenter();
    }

    @Override
    public void showLoading() {
        progressView.show();
    }

    @Override
    public void hideLoading() {
        progressView.hide();
    }

    @Override
    public void showError() {
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResult(AvailableLanguages availableLanguages,
                         String lastSelectedLanguage,
                         List<Language> lastUsedLanguages) {
        errorView.setVisibility(View.GONE);
        setUpRecyclerView(availableLanguages, lastSelectedLanguage, lastUsedLanguages);
    }

    @Override
    public String languageChooseType() {
        return getIntent().getStringExtra(LANGUAGE_CHOOSE_TYPE_INTENT_KEY);
    }

    @Override
    public void setResultOkAndFinish() {
        setResult(RESULT_OK);
        finish();
        enableExitAnimation();
    }

    @Override
    public void setResultCancel() {
        setResult(RESULT_CANCELED);
    }

    private void setUpRecyclerView(AvailableLanguages availableLanguages,
                                   String lastSelectedLanguage,
                                   List<Language> lastUsedLanguages) {
        List<Language> languageList = availableLanguages.getLanguageList();
        ChooseLanguageRecyclerAdapter adapter = new ChooseLanguageRecyclerAdapter(
                languageList, lastUsedLanguages, lastSelectedLanguage);
        adapter.setItemSelectListener(this);
        chooseLanguageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chooseLanguageRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.back_arrow)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onItemSelected(Language language) {
        presenter.handleLanguageSelection(language);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.handleBackPress();
        enableExitAnimation();
    }

    @OnClick(R.id.retry_button)
    public void onRetryClicked() {
        presenter.retry();
    }
}
