package com.luseen.yandexsummerschool.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.Animatable2Compat;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.model.History;
import com.luseen.yandexsummerschool.ui.adapter.view_holder.HistoryAndFavouriteViewHolder;
import com.luseen.yandexsummerschool.utils.AnimationUtils;
import com.luseen.yandexsummerschool.utils.CommonUtils;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class HistoryAndFavouriteRecyclerAdapter extends RecyclerView.Adapter {

    public interface AdapterItemClickListener {
        void onAdapterItemClick(History history);

        void onFavouriteClicked(boolean isFavourite, String identifier);
    }

    private AdapterItemClickListener adapterItemClickListener;
    private List<History> historyList;

    public HistoryAndFavouriteRecyclerAdapter(List<History> historyList) {
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
                if (adapterItemClickListener != null) {
                    adapterItemClickListener.onAdapterItemClick(history);
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

                if (history.isFavourite()) {
                    if (CommonUtils.isLollipopOrHigher()) {
                        AnimatedVectorDrawableCompat addFavAnimation =
                                AnimationUtils.createAnimatedVector(R.drawable.add_fav_anim);
                        holder.getFavouriteIcon().setImageDrawable(addFavAnimation);
                        addFavAnimation.start();
                        addFavAnimation.registerAnimationCallback(getAnimationCallback(history));
                    } else {
                        holder.getFavouriteIcon().setImageResource(R.drawable.bookmark_check);
                        makeFavouriteCallBack(history);
                    }
                } else {
                    if (CommonUtils.isLollipopOrHigher()) {
                        AnimatedVectorDrawableCompat removeFavAnimation =
                                AnimationUtils.createAnimatedVector(R.drawable.remove_fav_anim);
                        holder.getFavouriteIcon().setImageDrawable(removeFavAnimation);
                        removeFavAnimation.start();
                        removeFavAnimation.registerAnimationCallback(getAnimationCallback(history));
                    } else {
                        holder.getFavouriteIcon().setImageResource(R.drawable.bookmark_outline);
                        makeFavouriteCallBack(history);
                    }
                }
            }
        });

        return holder;
    }

    private void makeFavouriteCallBack(History history) {
        if (adapterItemClickListener != null) {
            adapterItemClickListener.onFavouriteClicked(history.isFavourite(),
                    history.getIdentifier());
        }
    }

    private Animatable2Compat.AnimationCallback getAnimationCallback(History history) {
        return new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                makeFavouriteCallBack(history);
            }
        };
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

    public void updateAdapterList(List<History> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();

        //It is not works as i expected, so comment it ...
//        DiffCallback diffCallback = new DiffCallback(this.historyList, historyList);
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
//        diffResult.dispatchUpdatesTo(this);
    }

    public void setAdapterItemClickListener(AdapterItemClickListener adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }
}
