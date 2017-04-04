package com.luseen.yandexsummerschool.ui.adapter.view_holder;

import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luseen.yandexsummerschool.App;
import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.model.LanguagePair;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;

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
        String language = pair.getLookupLanguage().toUpperCase();
        translationLanguageTextView.setText(language);
        if (history.isFavourite()) {
            int favouriteColor = ContextCompat.getColor(App.getInstance(), R.color.colorPrimary);
            favouriteIcon.setColorFilter(favouriteColor, PorterDuff.Mode.SRC_IN);
        } else {
            int defaultColor = ContextCompat.getColor(App.getInstance(), R.color.light_gray);
            favouriteIcon.setColorFilter(defaultColor, PorterDuff.Mode.SRC_IN);
        }
    }

    public ImageView getFavouriteIcon() {
        return favouriteIcon;
    }
}
