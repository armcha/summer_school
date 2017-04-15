package com.luseen.yandexsummerschool.ui.fragment.translation;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
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
import com.luseen.yandexsummerschool.model.event_bus_events.FavouriteEvent;
import com.luseen.yandexsummerschool.model.event_bus_events.ResetEvent;
import com.luseen.yandexsummerschool.ui.activity.choose_language.ChooseLanguageActivity;
import com.luseen.yandexsummerschool.ui.activity.root.RootActivity;
import com.luseen.yandexsummerschool.ui.widget.AnimatedTextView;
import com.luseen.yandexsummerschool.ui.widget.CloseIcon;
import com.luseen.yandexsummerschool.ui.widget.DictionaryView;
import com.luseen.yandexsummerschool.ui.widget.TranslationTextView;
import com.luseen.yandexsummerschool.ui.widget.TranslationView;
import com.luseen.yandexsummerschool.ui.widget.YaProgressView;
import com.luseen.yandexsummerschool.utils.AnimationUtils;
import com.luseen.yandexsummerschool.utils.CommonUtils;
import com.luseen.yandexsummerschool.utils.KeyboardUtils;
import com.luseen.yandexsummerschool.utils.Logger;
import com.luseen.yandexsummerschool.utils.RxUtils;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class TranslationFragment extends ApiFragment<TranslationFragmentContract.View, TranslationFragmentContract.Presenter>
        implements TranslationFragmentContract.View,
        CloseIcon.CloseIconClickListener,
        KeyboardVisibilityEventListener {

    public static final int CHOOSE_LANGUAGE_REQUEST_CODE = 1 << 1;
    public static final int TRANSLATION_FRAGMENT_POSITION = 0;
    public static final int KEYBOARD_OPEN_DELAY = 500;
    public static final long DEBOUNCE_TIMEOUT = 500L;

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

    @BindView(R.id.favourite_icon)
    FloatingActionButton favouriteIcon;

    @BindView(R.id.error_view)
    LinearLayout errorView;

    private CompositeSubscription subscriptions = new CompositeSubscription();
    private Unregistrar unregistrar;
    private DictionaryView dictView;
    private String currentIdentifier;
    private boolean isFavouriteIconSet;

    public static TranslationFragment newInstance() {
        return new TranslationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_translation, container, false);
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
        unregistrar = KeyboardVisibilityEvent.registerEventListener(getActivity(), this);
        favouriteIcon.hide();

        //Some hack to open keyboard on fragment start,
        //whit stateVisible mode it opens keyboard on history and setting screen on screen orientation change
        subscriptions.add(Observable.timer(KEYBOARD_OPEN_DELAY, TimeUnit.MILLISECONDS)
                .map(aLong -> ((RootActivity) getActivity()).getCurrentFragmentPosition())
                .filter(currentPosition -> currentPosition == TRANSLATION_FRAGMENT_POSITION)
                .subscribe(aLong -> KeyboardUtils.showKeyboard(rootLayout)));
    }

    @Override
    public void onResume() {
        super.onResume();
        //translationView.enable();
        //KeyboardUtils.showKeyboard(rootLayout);
        // FIXME: 10.04.2017 hide keyboard after opening lock screen
//        if (KeyboardVisibilityEvent.isKeyboardVisible(getActivity())) {
//            KeyboardUtils.hideKeyboard(rootLayout);
//            if (translationView.isEnable()) {
//                translationView.disable();
//            }
//        }
    }

    private void setUpDictView() {
        dictView = new DictionaryView(getActivity());
        rootLayout.addView(dictView);
        dictView.setOnClickListener(v -> {
            if (translationView.isEnable()) {
                translationView.disable();
                KeyboardUtils.hideKeyboard(rootLayout);
            } else {
                translationView.enable();
                KeyboardUtils.showKeyboard(rootLayout);
            }
        });
    }

    private void setUpTextWatcher() {
        subscriptions.add(RxTextView.textChanges(translationView.getTranslationEditText())
                .observeOn(AndroidSchedulers.mainThread())
                .map(CharSequence::toString)
                .doOnNext(input -> {
                    if (input.isEmpty()) {
                        reset();
                    }
                })
                .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .map(String::trim)
                .filter(input -> !input.isEmpty())
                .subscribe(input -> presenter.handleInputText(input)));
    }

    private void reset() {
        presenter.clearLastInputAndTranslate();
        translationTextView.reset();
        progressView.hide();
        dictView.reset();
        favouriteIcon.hide();
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxUtils.unsubscribe(subscriptions);
        if (unregistrar != null) {
            unregistrar.unregister();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected boolean whitButterKnife() {
        return true;
    }

    @OnClick({R.id.root_layout, R.id.scroll_view})
    public void onClick() {
        if (translationView.isEnable()) {
            translationView.disable();
            KeyboardUtils.hideKeyboard(rootLayout);
        } else {
            translationView.enable();
            KeyboardUtils.showKeyboard(rootLayout);
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
        errorView.setVisibility(View.VISIBLE);
        dictView.reset();
        translationTextView.reset();
    }

    @Override
    public void onTranslationResult(Translation translation, String identifier) {
        errorView.setVisibility(View.GONE);
        if (translationView.hasText()) {
            translationTextView.setText(translation.getTranslatedText());
            dictView.reset();
            currentIdentifier = identifier;
            setUpFavouriteIcon(translation.isFavourite(), identifier);
        }
    }

    @Override
    public void onDictionaryResult(Dictionary dictionary, String identifier) {
        errorView.setVisibility(View.GONE);
        if (translationView.hasText()) {
            translationTextView.setText(dictionary.getTranslatedText());
            dictView.updateDictionary(dictionary);
            currentIdentifier = identifier;
            setUpFavouriteIcon(dictionary.isFavourite(), identifier);
        }
    }

    private void setUpFavouriteIcon(boolean isFavourite, String identifier) {
        if (currentIdentifier.equals(identifier)) {
            if (isFavourite) {
                if (CommonUtils.isLollipopOrHigher()) {
                    AnimatedVectorDrawableCompat addFavAnimation =
                            AnimationUtils.createAnimatedVector(R.drawable.add_fav_anim_white);
                    favouriteIcon.setImageDrawable(addFavAnimation);
                    addFavAnimation.start();
                } else {
                    favouriteIcon.setImageResource(R.drawable.bookmark_check_black);
                }

                isFavouriteIconSet = true;
            } else {
                if (isFavouriteIconSet && CommonUtils.isLollipopOrHigher()) {
                    AnimatedVectorDrawableCompat removeFavAnimation =
                            AnimationUtils.createAnimatedVector(R.drawable.remove_fav_anim_white);
                    favouriteIcon.setImageDrawable(removeFavAnimation);
                    removeFavAnimation.start();
                    isFavouriteIconSet = false;
                } else {
                    favouriteIcon.setImageResource(R.drawable.bookmark_outline_black);
                }
            }
            favouriteIcon.show();
        }
    }

    @Subscribe
    public void onFavouriteEvent(FavouriteEvent favouriteEvent) {
        Logger.log("FAVOURITE EVENT " + favouriteEvent.getIdentifier());
        setUpFavouriteIcon(favouriteEvent.isFavourite(), favouriteEvent.getIdentifier());
    }

    @Override
    public void updateToolbarLanguages(LanguagePair languagePair) {
        String sourceLanguage = languagePair.getSourceLanguage().getFullLanguageName();
        String targetLanguage = languagePair.getTargetLanguage().getFullLanguageName();
        sourceLanguageTextView.setText(sourceLanguage);
        targetLanguageTextView.setText(targetLanguage);
    }

    @Override
    public void changeFavouriteIconState(boolean isFavourite, String identifier) {
        setUpFavouriteIcon(isFavourite, identifier);
    }

    @Override
    public void setTranslationViewText(String text) {
        translationView.getTranslationEditText().setText(text);
        translationView.getTranslationEditText().setSelection(text.length());
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
        KeyboardUtils.showKeyboard(rootLayout);
    }

    @Subscribe
    public void resetAll(ResetEvent resetEvent) {
        if (translationView.hasText()) {
            translationView.resetTextAndIcon();
            reset();
        }
    }

    @OnClick({R.id.source_language_text_view, R.id.target_language_text_view, R.id.swap_languages})
    public void onViewClicked(View view) {
        presenter.handleToolbarClicks(view.getId());
    }

    @OnClick(R.id.favourite_icon)
    public void onFavouriteClick() {
        presenter.setFavourite(currentIdentifier);
    }

    @Override
    public void onVisibilityChanged(boolean isOpen) {
        if (!isOpen) {
            translationView.disable();
        }
    }

    @OnClick(R.id.retry_button)
    public void onRetryClicked() {
        presenter.retry(translationView.getTranslationEditText().getText().toString());
    }
}
