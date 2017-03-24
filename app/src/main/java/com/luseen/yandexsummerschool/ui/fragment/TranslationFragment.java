package com.luseen.yandexsummerschool.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.api.ApiFragment;
import com.luseen.yandexsummerschool.model.Dictionary;
import com.luseen.yandexsummerschool.model.Translation;
import com.luseen.yandexsummerschool.ui.widget.CloseIcon;
import com.luseen.yandexsummerschool.ui.widget.DictionaryView;
import com.luseen.yandexsummerschool.ui.widget.TranslationTextView;
import com.luseen.yandexsummerschool.ui.widget.TranslationView;

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

    @BindView(R.id.translation_view)
    TranslationView translationView;

    @BindView(R.id.root_layout)
    LinearLayout rootLayout;

    @BindView(R.id.scroll_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.translation_text_view)
    TranslationTextView translationTextView;

    private Subscription textWatcherSubscription;
    private Unregistrar unregistrar;
    private DictionaryView dictView;

    public static TranslationFragment newInstance() {
        Bundle args = new Bundle();
        TranslationFragment fragment = new TranslationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_translation, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: 20.03.2017 test
        if (!translationView.hasText())
            translationView.enable();
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
                .debounce(500L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .map(CharSequence::toString)
                .filter(input -> !input.isEmpty())
                .map(String::trim)
                .subscribe(s -> presenter.handleInputText(s));
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

    }

    @Override
    public void hideLoading() {

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
    public void onClosePressed(CloseIcon closeIcon) {
        translationTextView.reset();
        translationView.reset();
        dictView.reset();
    }
}
