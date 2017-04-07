package com.luseen.yandexsummerschool.ui.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.luseen.yandexsummerschool.R;

/**
 * Created by Chatikyan on 06.04.2017.
 */

public class InfoShowerCoordinatorLayout extends CoordinatorLayout implements Viewable, InfoShower {

    private AppCompatImageView infoIconImageView;
    private TextView infoTextView;
    private View rootView;
    private String infoText;
    private int infoIcon;

    public InfoShowerCoordinatorLayout(Context context) {
        super(context);
        init(context);
    }

    public InfoShowerCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        rootView = inflater.inflate(R.layout.info_view, this, false);
        infoIconImageView = (AppCompatImageView) rootView.findViewById(R.id.info_icon);
        infoTextView = (TextView) rootView.findViewById(R.id.info_text);
        addView(rootView);

        rootView.setVisibility(GONE);
    }

    @Override
    public void showInfo() {
        rootView.setVisibility(VISIBLE);
    }

    @Override
    public void hideInfo() {
        rootView.setVisibility(GONE);
    }

    @Override
    public void setInfoText(String infoText) {
        this.infoText = infoText;
        infoTextView.setText(infoText);
    }

    @Override
    public void setInfoIcon(int infoIcon) {
        this.infoIcon = infoIcon;
        infoIconImageView.setImageResource(infoIcon);
    }
}
