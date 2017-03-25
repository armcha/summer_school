package com.luseen.yandexsummerschool.ui.activity.choose_language;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.api.ApiActivity;
import com.luseen.yandexsummerschool.model.AvailableLanguages;
import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.ui.adapter.ChooseLanguageRecyclerAdapter;

import java.util.List;

import butterknife.BindView;

public class ChooseLanguageActivity extends ApiActivity<ChooseLanguageContract.View, ChooseLanguageContract.Presenter>
        implements ChooseLanguageContract.View {

    @BindView(R.id.choose_language_recycler_view)
    RecyclerView chooseLanguageRecyclerView;

    public static Intent getStartIntent(Context context, String requestLanguage) {
        Intent intent = new Intent(context, ChooseLanguageActivity.class);
        intent.putExtra("RRR", requestLanguage);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
    }

    @NonNull
    @Override
    public ChooseLanguageContract.Presenter createPresenter() {
        return new ChooseLanguagePresenter();
    }

    @Override
    public String requestLanguage() {
        return getIntent().getStringExtra("RRR");
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
        ChooseLanguageRecyclerAdapter adapter = new ChooseLanguageRecyclerAdapter(languageList);
        chooseLanguageRecyclerView.setAdapter(adapter);
        chooseLanguageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
