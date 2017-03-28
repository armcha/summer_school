package com.luseen.yandexsummerschool.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.ui.adapter.view_holder.ChooseLanguageTextSectionViewHolder;
import com.luseen.yandexsummerschool.ui.adapter.view_holder.ChooseLanguageViewHolder;
import com.luseen.yandexsummerschool.utils.Logger;

import java.util.List;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class ChooseLanguageRecyclerAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_LANGUAGE = 0;
    private final int VIEW_TYPE_TEXT_SECTION = 1;
    private List<Language> languageList;
    private List<Language> lastUsedLanguageList;
    private boolean hasLastUsedLanguages;
    private int textSectionSize;

    public ChooseLanguageRecyclerAdapter(List<Language> languageList, List<Language> lastUsedLanguageList) {
        this.languageList = languageList;
        this.lastUsedLanguageList = lastUsedLanguageList;
        hasLastUsedLanguages = lastUsedLanguageList.size() > 0;
        textSectionSize = hasLastUsedLanguages ? 2 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        int secondSectionPosition = hasLastUsedLanguages ? lastUsedLanguageList.size() + 1 : 0;
        if (position == 0 || position == secondSectionPosition) {
            return VIEW_TYPE_TEXT_SECTION;
        } else {
            return VIEW_TYPE_LANGUAGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == VIEW_TYPE_LANGUAGE) {
            holder = createChooseLanguageViewHolder(parent);
        } else {
            holder = createChooseLanguageTextSectionViewHolder(parent);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ChooseLanguageViewHolder) {
            Language language;
            if (position > 0 && position < lastUsedLanguageList.size() + 1 && hasLastUsedLanguages) {
                language = lastUsedLanguageList.get(holder.getAdapterPosition() - 1);
            } else {
                language = languageList.get(holder.getAdapterPosition() - (lastUsedLanguageList.size() + textSectionSize));
            }
            ChooseLanguageViewHolder viewHolder = (ChooseLanguageViewHolder) holder;
            viewHolder.bind(language);

        } else if (holder instanceof ChooseLanguageTextSectionViewHolder) {
            ChooseLanguageTextSectionViewHolder sectionHolder = (ChooseLanguageTextSectionViewHolder) holder;
            String sectionText;
            if (position == 0 && hasLastUsedLanguages) {
                sectionText = "Last used";
            } else {
                sectionText = "All languages";
            }
            sectionHolder.bind(sectionText);
        }
    }

    @Override
    public int getItemCount() {
        return languageList.size() + textSectionSize + lastUsedLanguageList.size();
    }

    private RecyclerView.ViewHolder createChooseLanguageViewHolder(ViewGroup parent) {
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

    private RecyclerView.ViewHolder createChooseLanguageTextSectionViewHolder(ViewGroup parent) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.choose_language_text_section_item, parent, false);
        return new ChooseLanguageTextSectionViewHolder(inflatedView);
    }
}
