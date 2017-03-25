package com.luseen.yandexsummerschool.ui.adapter.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.model.Language;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class ChooseLanguageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.language_name)
    TextView languageNameTextView;

    public ChooseLanguageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Language language) {
        languageNameTextView.setText(language.getFullLanguageName());
    }
}
