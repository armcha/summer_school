package com.luseen.yandexsummerschool.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.ui.adapter.view_holder.HistoryAndFavouriteViewHolder;

import java.util.List;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class HistoryAndFavouriteRecyclerAdapter extends RecyclerView.Adapter {

    private List<History> historyList;

    public HistoryAndFavouriteRecyclerAdapter(List<History> historyList) {
        this.historyList = historyList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.history_and_favourite_item, parent, false);
        RecyclerView.ViewHolder holder = new HistoryAndFavouriteViewHolder(inflatedView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // TODO: 02.04.2017 Item click
                }
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

    public void updateHistoryList(List<History> historyList) {
        this.historyList.clear();
        this.historyList = historyList;
        notifyDataSetChanged();
    }
}
