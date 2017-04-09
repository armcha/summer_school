package com.luseen.yandexsummerschool.utils.difutils;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.luseen.yandexsummerschool.model.History;

import java.util.List;

/**
 * Created by Chatikyan on 05.04.2017.
 */

public class DiffCallback extends DiffUtil.Callback {

    private List<History> oldHistory;
    private List<History> newHistory;

    public DiffCallback(List<History> oldHistory, List<History> newHistory) {
        this.newHistory = newHistory;
        this.oldHistory = oldHistory;
    }

    @Override
    public int getOldListSize() {
        return oldHistory.size();
    }

    @Override
    public int getNewListSize() {
        return newHistory.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        final History oldItem = oldHistory.get(oldItemPosition);
        final History newItem = newHistory.get(newItemPosition);
        return oldItem.getIdentifier().equals(newItem.getIdentifier());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final History oldItem = oldHistory.get(oldItemPosition);
        final History newItem = newHistory.get(newItemPosition);
        return oldItem.getDictionary().getOriginalText().equals(newItem.getDictionary().getOriginalText());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}