package com.luseen.yandexsummerschool.ui.activity.choose_language;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.api.ApiActivity;
import com.luseen.yandexsummerschool.model.AvailableLanguages;
import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.ui.adapter.ChooseLanguageRecyclerAdapter;
import com.luseen.yandexsummerschool.ui.widget.AnimatedTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseLanguageActivity extends ApiActivity<ChooseLanguageContract.View, ChooseLanguageContract.Presenter>
        implements ChooseLanguageContract.View {

    @BindView(R.id.choose_language_recycler_view)
    RecyclerView chooseLanguageRecyclerView;

    @BindView(R.id.toolbar_title)
    AnimatedTextView toolbarTitleTextView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String languageChooseType;

    public static Intent getStartIntent(Context context,
                                        String languageChooseType) {
        Intent intent = new Intent(context, ChooseLanguageActivity.class);
        intent.putExtra("RRR", languageChooseType);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        languageChooseType = getIntent().getStringExtra("RRR");
        presenter.startAvailableLanguagesRequest();
        setUpToolbar();
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        String toolbarTitle = "NULL";
        switch (languageChooseType) {
            case LanguageChooseType.TYPE_SOURCE:
                toolbarTitle = "SOURCE";
                break;
            case LanguageChooseType.TYPE_TARGET:
                toolbarTitle = "TARGET";
                break;
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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void onResult(AvailableLanguages availableLanguages) {
        setUpRecyclerView(availableLanguages);
    }

    private void setUpRecyclerView(AvailableLanguages availableLanguages) {
        List<Language> languageList = availableLanguages.getLanguageList();
        // TODO: 28.03.2017 add real last used list
        List<Language> lastUsedLanguageList = availableLanguages.getLanguageList().subList(0, 3);
        ChooseLanguageRecyclerAdapter adapter = new ChooseLanguageRecyclerAdapter(languageList, lastUsedLanguageList);
        chooseLanguageRecyclerView.setAdapter(adapter);
        chooseLanguageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.back_arrow)
    public void onViewClicked() {
        onBackPressed();
    }
}
