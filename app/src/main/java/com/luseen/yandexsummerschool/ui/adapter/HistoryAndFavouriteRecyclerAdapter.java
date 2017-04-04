package com.luseen.yandexsummerschool.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.ui.adapter.view_holder.HistoryAndFavouriteViewHolder;
import com.luseen.yandexsummerschool.utils.Logger;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class HistoryAndFavouriteRecyclerAdapter extends RecyclerView.Adapter {

    public interface HistoryItemClickListener {
        void onHistoryItemClick(History history);
    }

    private HistoryItemClickListener historyItemClickListener;
    private RealmResults<History> historyList;

    public HistoryAndFavouriteRecyclerAdapter(RealmResults<History> historyList) {
        this.historyList = historyList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.history_and_favourite_item, parent, false);
        HistoryAndFavouriteViewHolder holder = new HistoryAndFavouriteViewHolder(inflatedView);

        holder.itemView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                History history = historyList.get(position);
                if (historyItemClickListener != null) {
                    historyItemClickListener.onHistoryItemClick(history);
                }
            }
        });

        holder.getFavouriteIcon().setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                History history = historyList.get(position);
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                history.setFavourite(!history.isFavourite());
                realm.commitTransaction();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        History history = historyList.get(holder.getAdapterPosition());
        HistoryAndFavouriteViewHolder viewHolder = ((HistoryAndFavouriteViewHolder) holder);
        viewHolder.bind(history);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void updateHistoryList(RealmResults<History> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }

    public void setHistoryItemClickListener(HistoryItemClickListener historyItemClickListener) {
        this.historyItemClickListener = historyItemClickListener;
    }
}
