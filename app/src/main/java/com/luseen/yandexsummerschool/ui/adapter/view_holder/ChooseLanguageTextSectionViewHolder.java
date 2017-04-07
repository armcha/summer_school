package com.luseen.yandexsummerschool.ui.adapter.view_holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.luseen.yandexsummerschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chatikyan on 25.03.2017.
 */

public class ChooseLanguageTextSectionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.section_name)
    TextView sectionTextView;

    public ChooseLanguageTextSectionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(String sectionText) {
        sectionTextView.setText(sectionText);
    }
}
