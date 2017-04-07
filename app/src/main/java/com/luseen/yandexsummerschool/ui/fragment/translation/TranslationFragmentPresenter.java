package com.luseen.yandexsummerschool.ui.fragment.translation;

import android.app.Activity;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.Translation;
import com.luseen.yandexsummerschool.model.YaError;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;
import com.luseen.yandexsummerschool.model.event_bus_events.FavouriteEvent;
import com.luseen.yandexsummerschool.model.event_bus_events.HistoryEvent;
import com.luseen.yandexsummerschool.ui.activity.choose_language.LanguageChooseType;
import com.luseen.yandexsummerschool.utils.HttpUtils;
import com.luseen.yandexsummerschool.utils.Logger;
import com.luseen.yandexsummerschool.utils.RxUtil;
import com.luseen.yandexsummerschool.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Chatikyan on 20.03.2017.
 */

public class TranslationFragmentPresenter extends ApiPresenter<TranslationFragmentContract.View>
        implements TranslationFragmentContract.Presenter {

    private Subscription historySubscription;

    @Override
    public void onCreate() {
        super.onCreate();
        //Updating toolbar  language views
        //and set translation view text to be translated from language pair
        if (isViewAttached()) {
            getView().updateToolbarLanguages(dataManager.getLanguagePair());
            getView().setTranslationViewText(dataManager.getLastTypedText());
        }
    }

    @Override
    public void onStart(RequestType requestType) {
        if (isViewAttached()) {
            getView().showLoading();
        }
    }

    @Override
    public <T> void onSuccess(RequestType requestType, T response) {
        if (isViewAttached()) {
            getView().hideLoading();
            if (requestType == RequestType.TRANSLATION) {
                Translation translation = ((Translation) response);
                String historyIdentifier = getIdentifier(translation.getOriginalText());
                boolean isFavourite = isResponseFavourite(historyIdentifier);
                translation.setFavourite(isFavourite);

                dataManager.saveLastTranslatedWord(translation.getTranslatedText());

                createHistoryFromTranslationAndSave(translation);

                getView().onTranslationResult(translation, historyIdentifier);
            } else if (requestType == RequestType.LOOKUP) {
                Dictionary dictionary = ((Dictionary) response);
                String historyIdentifier = getIdentifier(dictionary.getOriginalText());
                boolean isFavourite = isResponseFavourite(historyIdentifier);
                dictionary.setFavourite(isFavourite);
                dataManager.saveLastTranslatedWord(dictionary.getTranslatedText());
                createHistoryFromDictionaryAndSave(dictionary);
                getView().onDictionaryResult(dictionary, historyIdentifier);
            }
        }
    }

    private String getIdentifier(String originalText) {
        LanguagePair pair = dataManager.getLanguagePair();
        String pairId = pair.getSourceLanguage().getLangCode() +
                pair.getTargetLanguage().getLangCode();
        return originalText + pairId;
    }

    //Checking if our response is in favourite list
    private boolean isResponseFavourite(String identifier) {
        Realm realm = Realm.getDefaultInstance();
        History historyFromDb = realm.where(History.class)
                .equalTo(History.IDENTIFIER, identifier)
                .findFirst();
        realm.close();
        return historyFromDb != null && historyFromDb.isFavourite();
    }

    //Create history object from Translation and save
    private void createHistoryFromTranslationAndSave(Translation translation) {
        Dictionary dictionary = new Dictionary();
        dictionary.setOriginalText(translation.getOriginalText());
        dictionary.setTranslatedText(translation.getTranslatedText());
        History history = new History(dictionary, dataManager.getLanguagePair());
        history.setFavourite(translation.isFavourite());
        saveHistory(history);
    }

    //Create history object from Dictionary and save
    private void createHistoryFromDictionaryAndSave(Dictionary dictionary) {
        History history = new History(dictionary, dataManager.getLanguagePair());
        history.setFavourite(dictionary.isFavourite());
        saveHistory(history);
    }

    private void saveHistory(History history) {
        dataManager.saveHistory(history);
        EventBus.getDefault().post(new HistoryEvent());
    }

    @Override
    public void onError(RequestType requestType, Throwable throwable) {
        if (isViewAttached()) {
            if (HttpUtils.getYaError(throwable) == YaError.LANGUAGE_IS_NOT_SUPPORTED) {
                //Making translation request, if dictionary request returns LANGUAGE_IS_NOT_SUPPORTED
                LanguagePair pair = dataManager.getLanguagePair();
                String translationLanguage = pair.getTargetLanguage().getLangCode();
                String inputText = dataManager.getLastTypedText();
                makeRequest(dataManager.translate(inputText, translationLanguage), RequestType.TRANSLATION);
            } else {
                getView().hideLoading();
                getView().showError();
            }
        }
    }

    @Override
    public void handleInputText(String inputText) {
        //Saving last typed text
        dataManager.saveLastTypedText(inputText);

        //Making both translate and dictionary request
        LanguagePair pair = dataManager.getLanguagePair();
        String translationLanguage = pair.getTargetLanguage().getLangCode();
        String lookUpLanguage = pair.getLookupLanguage();
        Observable<Dictionary> dictionaryObservable = dataManager.translateAndLookUp(
                inputText,
                translationLanguage,
                lookUpLanguage);
        makeRequest(dictionaryObservable, RequestType.LOOKUP);
    }

    @Override
    public void handleToolbarClicks(int id) {
        if (!isViewAttached()) return;

        switch (id) {
            case R.id.target_language_text_view:
                //Opening choose language activity in TARGET mode
                getView().openChooseLanguageActivity(LanguageChooseType.TYPE_TARGET);
                break;
            case R.id.source_language_text_view:
                //Opening choose language activity in SOURCE mode
                getView().openChooseLanguageActivity(LanguageChooseType.TYPE_SOURCE);
                break;
            case R.id.swap_languages:
                //Swap current languages, save it and make request
                LanguagePair dbLanguagePair = dataManager.getLanguagePair();
                Logger.log("dbLanguagePair " + dbLanguagePair);
                LanguagePair languagePair = new LanguagePair();
                languagePair.setSourceLanguage(dbLanguagePair.getTargetLanguage());
                languagePair.setTargetLanguage(dbLanguagePair.getSourceLanguage());
                Logger.log("Created  " + languagePair);
                dataManager.saveLanguagePair(languagePair);
                Logger.log("After save " + dataManager.getLanguagePair());

                getView().animateLanguageSwap(languagePair);
                getView().setTranslationViewText(dataManager.getLastTranslatedText());
                break;
        }
    }

    @Override
    public void handleActivityResult(int requestCode, int resultCode) {
        if (requestCode == TranslationFragment.CHOOSE_LANGUAGE_REQUEST_CODE &&
                resultCode == Activity.RESULT_OK && isViewAttached()) {
            getView().updateToolbarLanguages(dataManager.getLanguagePair());
            getView().setTranslationViewText(dataManager.getLastTypedText());
        }
    }

    @Override
    public void clearLastInputAndTranslate() {
        dataManager.saveLastTypedText(StringUtils.EMPTY);
        dataManager.saveLastTranslatedWord(StringUtils.EMPTY);
    }

    @Override
    public void setFavourite(String identifier) {
        boolean isFavourite = isResponseFavourite(identifier);
        historySubscription = dataManager.getHistoryByIdentifier(identifier)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(history -> {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    history.setFavourite(!history.isFavourite());
                    realm.commitTransaction();
                    EventBus.getDefault().post(new HistoryEvent());
                    EventBus.getDefault().post(new FavouriteEvent(isFavourite, identifier));
                    if (isViewAttached()) {
                        getView().changeFavouriteIconState(history.isFavourite(), identifier);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtil.unsubscribe(historySubscription);
    }
}
