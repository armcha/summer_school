package com.luseen.yandexsummerschool.ui.fragment.translation;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.api.ApiFragment;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.Translation;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;
import com.luseen.yandexsummerschool.ui.activity.choose_language.ChooseLanguageActivity;
import com.luseen.yandexsummerschool.ui.widget.AnimatedTextView;
import com.luseen.yandexsummerschool.ui.widget.CloseIcon;
import com.luseen.yandexsummerschool.ui.widget.DictionaryView;
import com.luseen.yandexsummerschool.ui.widget.TranslationTextView;
import com.luseen.yandexsummerschool.ui.widget.TranslationView;
import com.luseen.yandexsummerschool.ui.widget.YaProgressView;
import com.luseen.yandexsummerschool.utils.AnimationUtils;
import com.luseen.yandexsummerschool.utils.Logger;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class TranslationFragment extends ApiFragment<TranslationFragmentContract.View, TranslationFragmentContract.Presenter>
        implements TranslationFragmentContract.View,
        CloseIcon.CloseIconClickListener {

    public static final int CHOOSE_LANGUAGE_REQUEST_CODE = 1 << 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.translation_view)
    TranslationView translationView;

    @BindView(R.id.root_layout)
    LinearLayout rootLayout;

    @BindView(R.id.scroll_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.translation_text_view)
    TranslationTextView translationTextView;

    @BindView(R.id.source_language_text_view)
    AnimatedTextView sourceLanguageTextView;

    @BindView(R.id.target_language_text_view)
    AnimatedTextView targetLanguageTextView;

    @BindView(R.id.swap_languages)
    ImageView swapLanguagesIcon;

    @BindView(R.id.progress_view)
    YaProgressView progressView;

    private Subscription textWatcherSubscription;
    private Unregistrar unregistrar;
    private DictionaryView dictView;

    public static TranslationFragment newInstance() {
        return new TranslationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_translation, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (translationView.hasText()) {

            translationView.forceDisable();
            // FIXME: 30.03.2017 Hide keyboard
            // KeyboardUtils.hideKeyboard(rootLayout);
        } else {
            translationView.forceEnable();
        }
    }

    @NonNull
    @Override
    public TranslationFragmentContract.Presenter createPresenter() {
        return new TranslationFragmentPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpDictView();
        setUpTextWatcher();
        translationView.getCloseIcon().setCloseIconClickListener(this);

        unregistrar = KeyboardVisibilityEvent.registerEventListener(getActivity(), isOpen -> {
            if (!isOpen) translationView.disable();
        });
    }

    private void setUpDictView() {
        dictView = new DictionaryView(getActivity());
        rootLayout.addView(dictView);
    }

    private void setUpTextWatcher() {
        textWatcherSubscription = RxTextView.textChanges(translationView.getTranslationEditText())
                .observeOn(AndroidSchedulers.mainThread())
                .map(CharSequence::toString)
                .doOnNext(input -> {
                    if (input.isEmpty()) {
                        reset();
                    }
                })
                .debounce(500L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .filter(input -> !input.isEmpty())
                .map(String::trim)
                .subscribe(s -> presenter.handleInputText(s));
    }

    private void reset() {
        presenter.clearLastInputAndTranslate();
        translationTextView.reset();
        progressView.hide();
        dictView.reset();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (textWatcherSubscription != null && !textWatcherSubscription.isUnsubscribed()) {
            textWatcherSubscription.unsubscribe();
        }
        unregistrar.unregister();
    }

    @Override
    protected boolean whitButterKnife() {
        return true;
    }

    @OnClick({R.id.root_layout, R.id.scroll_view})
    public void onClick() {
        if (translationView.isEnable()) {
            translationView.disable();
        }
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

    }

    @Override
    public void onTranslationResult(Translation translation) {
        if (translationView.hasText()) {
            translationTextView.setText(translation.getTranslatedText());
            dictView.reset();
        }
    }

    @Override
    public void onDictionaryResult(Dictionary dictionary) {
        if (translationView.hasText()) {
            translationTextView.setText(dictionary.getTranslatedText());
            dictView.updateDictionary(dictionary);
        }
    }

    @Override
    public void updateToolbarLanguages(LanguagePair languagePair) {
        String sourceLanguage = languagePair.getSourceLanguage().getFullLanguageName();
        String targetLanguage = languagePair.getTargetLanguage().getFullLanguageName();
        sourceLanguageTextView.setText(sourceLanguage);
        targetLanguageTextView.setText(targetLanguage);
    }

    @Override
    public void setTranslationViewText(String text) {
        translationView.getTranslationEditText().setText(text);
        // TODO: 31.03.2017 need testing
        //translationView.disable();
    }

    @Override
    public void animateLanguageSwap(LanguagePair languagePair) {
        String sourceLanguage = languagePair.getSourceLanguage().getFullLanguageName();
        String targetLanguage = languagePair.getTargetLanguage().getFullLanguageName();
        AnimationUtils.makeRotateAnimation(swapLanguagesIcon);
        sourceLanguageTextView.setAnimatedText(sourceLanguage);
        targetLanguageTextView.setAnimatedText(targetLanguage);
    }

    @Override
    public void openChooseLanguageActivity(String languageChooseType) {
        Intent startIntent = ChooseLanguageActivity.getStartIntent(getActivity(), languageChooseType);
        startActivityForResult(startIntent, CHOOSE_LANGUAGE_REQUEST_CODE);
        enableEnterAnimation();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.handleActivityResult(requestCode, resultCode);
    }

    @Override
    public void onClosePressed(CloseIcon closeIcon) {
        translationView.reset();
        reset();
    }

    @OnClick({R.id.source_language_text_view, R.id.target_language_text_view, R.id.swap_languages})
    public void onViewClicked(View view) {
        presenter.handleToolbarClicks(view.getId());
    }
}
