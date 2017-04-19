package com.luseen.yandexsummerschool.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.luseen.yandexsummerschool.App;
import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.model.Language;
import com.luseen.yandexsummerschool.ui.adapter.view_holder.ChooseLanguageTextSectionViewHolder;
import com.luseen.yandexsummerschool.ui.adapter.view_holder.ChooseLanguageViewHolder;

import java.util.List;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class ChooseLanguageRecyclerAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_TEXT_SECTION = 1;
    private final int VIEW_TYPE_LANGUAGE = 0;

    private ChooseLanguageItemSelectListener itemSelectListener;
    private List<Language> languageList;
    private List<Language> lastUsedLanguageList;
    private String lastSelectedLanguage;
    private boolean hasLastUsedLanguages;
    private int textSectionSize;

    public ChooseLanguageRecyclerAdapter(List<Language> languageList,
                                         List<Language> lastUsedLanguageList,
                                         String lastSelectedLanguage) {
        this.languageList = languageList;
        this.lastUsedLanguageList = lastUsedLanguageList;
        this.lastSelectedLanguage = lastSelectedLanguage;
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
        Context context = App.getInstance();
        if (holder instanceof ChooseLanguageViewHolder) {
            Language language = getCurrentLanguage(holder.getAdapterPosition());
            ChooseLanguageViewHolder viewHolder = (ChooseLanguageViewHolder) holder;
            ImageView checkLanguageIcon = viewHolder.getCheckLanguageIcon();
            boolean needShowCheckIcon = position >= (textSectionSize + lastUsedLanguageList.size());

            if (lastSelectedLanguage.equalsIgnoreCase(language.getLangCode()) && needShowCheckIcon) {
                checkLanguageIcon.setVisibility(View.VISIBLE);
                int selectedItemColor = ContextCompat.getColor(context, R.color.selected_item_color);
                holder.itemView.setBackgroundColor(selectedItemColor);
            } else {
                checkLanguageIcon.setVisibility(View.GONE);
                holder.itemView.setBackgroundColor(Color.WHITE);
            }

            viewHolder.bind(language);
        } else if (holder instanceof ChooseLanguageTextSectionViewHolder) {
            ChooseLanguageTextSectionViewHolder sectionHolder = (ChooseLanguageTextSectionViewHolder) holder;
            String sectionText;
            if (position == 0 && hasLastUsedLanguages) {
                sectionText = context.getString(R.string.last_used_languages);
            } else {
                sectionText = context.getString(R.string.all_languages);
            }
            sectionHolder.bind(sectionText);
        }
    }

    @Override
    public int getItemCount() {
        return languageList.size() + textSectionSize + lastUsedLanguageList.size();
    }

    private Language getCurrentLanguage(int position) {
        Language language;
        if (position > 0 && position < lastUsedLanguageList.size() + 1 && hasLastUsedLanguages) {
            language = lastUsedLanguageList.get(position - 1);
        } else {
            language = languageList.get(position - (lastUsedLanguageList.size() + textSectionSize));
        }
        return language;
    }

    private RecyclerView.ViewHolder createChooseLanguageViewHolder(ViewGroup parent) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.choose_language_item, parent, false);

        ChooseLanguageViewHolder chooseLanguageViewHolder = new ChooseLanguageViewHolder(inflatedView);
        chooseLanguageViewHolder.itemView.setOnClickListener(view -> {
            int position = chooseLanguageViewHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Language language = getCurrentLanguage(position);
                if (itemSelectListener != null) {
                    itemSelectListener.onItemSelected(language);
                }
            }
        });
        return chooseLanguageViewHolder;
    }

    private RecyclerView.ViewHolder createChooseLanguageTextSectionViewHolder(ViewGroup parent) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.choose_language_text_section_item, parent, false);
        return new ChooseLanguageTextSectionViewHolder(inflatedView);
    }

    public void setItemSelectListener(ChooseLanguageItemSelectListener itemSelectListener) {
        this.itemSelectListener = itemSelectListener;
    }
}
