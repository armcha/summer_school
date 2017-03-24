package com.luseen.yandexsummerschool.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.util.StateSet;
import android.view.View;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.utils.CommonUtils;

/**
 * Created by Chatikyan on 20.03.2017.
 */

public class CloseIcon extends AppCompatImageView implements View.OnClickListener, Viewable {

    public interface CloseIconClickListener {
        void onClosePressed(CloseIcon closeIcon);
    }

    private CloseIconClickListener closeIconClickListener;

    public CloseIcon(Context context) {
        super(context);
        init(context);
    }

    @Override
    public void init(Context context) {
        int[] attrs = {R.attr.selectableItemBackgroundBorderless};
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        Drawable foreground = ContextCompat.getDrawable(context, backgroundResource);
        typedArray.recycle();
        setVisibility(GONE);
        StateListDrawable stateListDrawable = new StateListDrawable();
        // TODO: 24.03.2017 support api > 21
       // Drawable drawablePressed = ContextCompat.getDrawable(context, R.drawable.close_icon_pressed);
       // Drawable drawableNormal = ContextCompat.getDrawable(context, R.drawable.close_icon);
       // stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed);
       // stateListDrawable.addState(StateSet.WILD_CARD, drawableNormal);
        if (CommonUtils.isMarshmallowOrHigher()) {
            setForeground(foreground);
        }
        setImageDrawable(stateListDrawable);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (closeIconClickListener != null) {
            closeIconClickListener.onClosePressed(this);
        }
    }

    public void show() {
        if (getVisibility() == VISIBLE) {
            return;
        }
        setVisibility(VISIBLE);
        setScaleX(0);
        setScaleY(0);

        ViewCompat.animate(this)
                .setDuration(250)
                .scaleX(1)
                .scaleY(1)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(View view) {
                        setVisibility(VISIBLE);
                    }
                })
                .start();
    }

    public void hide() {
        ViewCompat.animate(this)
                .setDuration(250)
                .scaleX(0)
                .scaleY(0)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(final View view) {
                        setVisibility(GONE);
                    }
                })
                .start();
    }

    public void setCloseIconClickListener(CloseIconClickListener closeIconClickListener) {
        this.closeIconClickListener = closeIconClickListener;
    }
}
