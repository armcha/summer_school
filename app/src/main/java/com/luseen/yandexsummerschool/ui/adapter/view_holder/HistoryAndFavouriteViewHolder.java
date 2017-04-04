package com.luseen.yandexsummerschool.ui.adapter.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;
import com.luseen.yandexsummerschool.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class HistoryAndFavouriteViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.original_text)
    TextView originalTextView;

    @BindView(R.id.translated_text)
    TextView translatedTextView;

    @BindView(R.id.translation_language_text)
    TextView translationLanguageTextView;

    @BindView(R.id.favourite_icon)
    ImageView favouriteIcon;

    public HistoryAndFavouriteViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(History history) {
        Dictionary dictionary = history.getDictionary();
        LanguagePair pair = history.getLanguagePair();
        originalTextView.setText(dictionary.getOriginalText());
        translatedTextView.setText(dictionary.getTranslatedText());
        String language = pair.getSourceLanguage().getLangCode().toUpperCase() +
                "-" + pair.getTargetLanguage().getLangCode().toUpperCase();
        translationLanguageTextView.setText(language);
        if (history.isFavourite()) {
            // TODO: 02.04.2017
        }
    }
}
