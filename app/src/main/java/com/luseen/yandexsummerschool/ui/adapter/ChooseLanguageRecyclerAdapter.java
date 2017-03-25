package com.luseen.yandexsummerschool.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.ui.adapter.view_holder.ChooseLanguageViewHolder;
import com.luseen.yandexsummerschool.utils.Logger;

import java.util.List;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class ChooseLanguageRecyclerAdapter extends RecyclerView.Adapter {

    private List<Language> languageList;

    public ChooseLanguageRecyclerAdapter(List<Language> languageList) {
        this.languageList = languageList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.choose_language_item, parent, false);

        ChooseLanguageViewHolder chooseLanguageViewHolder = new ChooseLanguageViewHolder(inflatedView);

        chooseLanguageViewHolder.itemView.setOnClickListener(view -> {
            int position = chooseLanguageViewHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Language language = languageList.get(chooseLanguageViewHolder.getAdapterPosition());
                Logger.log(language.getLangCode());
            }
        });
        return chooseLanguageViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Language language = languageList.get(holder.getAdapterPosition());
        ChooseLanguageViewHolder viewHolder = (ChooseLanguageViewHolder) holder;
        viewHolder.bind(language);
    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }
}
