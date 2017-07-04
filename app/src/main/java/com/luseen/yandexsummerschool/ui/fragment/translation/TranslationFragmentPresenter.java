package com.luseen.yandexsummerschool.ui.fragment.translation;

import android.app.Activity;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.api.ApiPresenter;
import com.luseen.yandexsummerschool.data.api.ApiHelper;
import com.luseen.yandexsummerschool.data.api.RequestType;
import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.Translation;
import com.luseen.yandexsummerschool.model.YaError;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;
import com.luseen.yandexsummerschool.model.event_bus_events.FavouriteEvent;
import com.luseen.yandexsummerschool.model.event_bus_events.HistoryEvent;
import com.luseen.yandexsummerschool.ui.activity.choose_language.LanguageChooseType;
import com.luseen.yandexsummerschool.utils.ExceptionTracker;
import com.luseen.yandexsummerschool.utils.HttpUtils;
import com.luseen.yandexsummerschool.utils.LanguageUtils;
import com.luseen.yandexsummerschool.utils.NetworkUtils;
import com.luseen.yandexsummerschool.utils.RxUtils;
import com.luseen.yandexsummerschool.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Chatikyan on 20.03.2017.
 */

public class TranslationFragmentPresenter extends ApiPresenter<TranslationFragmentContract.View>
        implements TranslationFragmentContract.Presenter {

    private final CompositeSubscription historySubscriptions = new CompositeSubscription();

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

    //Show loading when request started
    @Override
    public void onStart(RequestType requestType) {
        //no-op
    }

    //Request finished on success
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
                //Giving result to view
                getView().onTranslationResult(translation, historyIdentifier);
            } else if (requestType == RequestType.LOOKUP) {
                Dictionary dictionary = ((Dictionary) response);
                String historyIdentifier = getIdentifier(dictionary.getOriginalText());
                boolean isFavourite = isResponseFavourite(historyIdentifier);
                dictionary.setFavourite(isFavourite);

                dataManager.saveLastTranslatedWord(dictionary.getTranslatedText());
                createHistoryFromDictionaryAndSave(dictionary);
                //Giving result to view
                getView().onDictionaryResult(dictionary, historyIdentifier, false);
            }
        }
    }

    //Request finished on error
    @Override
    public void onError(RequestType requestType, Throwable throwable) {
        if (isViewAttached()) {
            if (HttpUtils.getYaError(throwable) == YaError.LANGUAGE_IS_NOT_SUPPORTED) {
                //Making translation request, if dictionary request returns LANGUAGE_IS_NOT_SUPPORTED
                LanguagePair pair = dataManager.getLanguagePair();
                String translationLanguage = pair.getLookupLanguage();
                String inputText = dataManager.getLastTypedText();
                makeRequest(dataManager.translate(inputText, translationLanguage), RequestType.TRANSLATION);
            } else {
                getView().hideLoading();
                getView().showError();
            }
        }
    }

    //Getting current history identifier
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

    //Saving history and notifying history fragment to to reload data
    private void saveHistory(History history) {
        historySubscriptions.add(dataManager.saveHistory(history)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> EventBus.getDefault().post(new HistoryEvent())));
    }

    //handling user input
    @Override
    public void handleInputText(String inputText) {
        if (!isViewAttached()) {
            return;
        }

        //Saving last typed text
        dataManager.saveLastTypedText(inputText);

        String historyIdentifier = getIdentifier(inputText);
        historySubscriptions.add(dataManager.getHistoryByIdentifier(historyIdentifier)
                .doOnSubscribe(() -> getView().showLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(history -> {
                    boolean hasResultInDb = history != null && history.isValid();
                    if (hasResultInDb) {//If we had history in db, just showing from db, otherwise making request
                        dictionaryResultFromDb(history, false);
                    } else {
                        //check if network is available, than showing onError or making request
                        if (!NetworkUtils.isNetworkAvailable()) {
                            if (isViewAttached()) {
                                getView().hideLoading();
                                getView().showError();
                            }
                            return;
                        }
                        //Making both translate and dictionary request
                        makeLookUpAndTranslateRequest(inputText);
                    }
                }, throwable -> {
                    ExceptionTracker.trackException(throwable);
                    getView().hideLoading();
                    getView().showError();
                }));
    }

    /**
     * Getting dictionary from history,
     * and checking if history from received from history or favourite fragment
     *
     * @param history                History from db
     * @param fromHistoryOrFavourite
     */
    private void dictionaryResultFromDb(History history, boolean fromHistoryOrFavourite) {
        Dictionary dictionary = history.getDictionary();
        //Saving favourite
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        dictionary.setFavourite(history.isFavourite());
        realm.commitTransaction();
        realm.close();
        dataManager.saveLastTranslatedWord(dictionary.getTranslatedText());
        if (isViewAttached()) {
            getView().hideLoading();
            getView().onDictionaryResult(dictionary, history.getIdentifier(), fromHistoryOrFavourite);
        }
    }

    /**
     * Making both lookUp and translate request, based on input text
     *
     * @param inputText input text
     */
    private void makeLookUpAndTranslateRequest(String inputText) {
        LanguagePair pair = dataManager.getLanguagePair();
        String lookUpLanguage = pair.getLookupLanguage();
        String uiLang = LanguageUtils.getCurrentLocal().toString();
        Observable<Dictionary> dictionaryObservable = dataManager.translateAndLookUp(
                inputText,
                lookUpLanguage,
                uiLang,
                ApiHelper.SHORT_POS);
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
                LanguagePair languagePair = new LanguagePair();
                languagePair.setSourceLanguage(dbLanguagePair.getTargetLanguage());
                languagePair.setTargetLanguage(dbLanguagePair.getSourceLanguage());
                dataManager.saveLanguagePair(languagePair);

                getView().animateLanguageSwap(languagePair);
                getView().setTranslationViewText(dataManager.getLastTranslatedText());
                break;
        }
    }

    /**
     * Handling language choose, from choose language activity
     *
     * @param requestCode code, that activity started
     * @param resultCode  activity result
     */
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

    /**
     * Finding and setting favourite, by history identifier
     *
     * @param identifier given identifier
     */
    @Override
    public void setFavourite(String identifier) {
        boolean isFavourite = isResponseFavourite(identifier);
        historySubscriptions.add(dataManager.getHistoryByIdentifier(identifier)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(history -> {
                    //Saving...
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    history.setFavourite(!history.isFavourite());
                    realm.commitTransaction();

                    //Notifying history and favourite fragments favourite changing
                    EventBus.getDefault().post(new HistoryEvent());
                    EventBus.getDefault().post(new FavouriteEvent(isFavourite, identifier));
                    if (isViewAttached()) {
                        getView().changeFavouriteIconState(history.isFavourite(), identifier);
                    }
                }));
    }

    @Override
    public void retry(String inputText) {
        handleInputText(inputText);
    }

    /**
     * //Handling history receiving from history or favourite fragment
     *
     * @param history received history
     */
    @Override
    public void handleHistoryReceiving(History history) {
        //Saving new language pair
        dataManager.saveLastTypedText(history.getDictionary().getOriginalText());
        LanguagePair historyLangPair = history.getLanguagePair();
        LanguagePair languagePair = new LanguagePair();
        languagePair.setSourceLanguage(historyLangPair.getSourceLanguage());
        languagePair.setTargetLanguage(historyLangPair.getTargetLanguage());
        dataManager.saveLanguagePair(languagePair);

        if (isViewAttached())
            getView().updateToolbarLanguages(history.getLanguagePair());
        dictionaryResultFromDb(history, true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtils.unsubscribe(historySubscriptions);
    }
}
