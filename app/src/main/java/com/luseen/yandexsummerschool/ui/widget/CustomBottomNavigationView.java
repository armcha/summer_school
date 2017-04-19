package com.luseen.yandexsummerschool.ui.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.utils.ExceptionTracker;

import java.lang.reflect.Field;

/**
 * Created by Chatikyan on 10.04.2017.
 */

public class CustomBottomNavigationView extends BottomNavigationView {

    private final Context context;

    public CustomBottomNavigationView(Context context) {
        super(context);
        this.context = context;
    }

    public CustomBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CustomBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        try {
            Field itemInactiveColor = BottomNavigationView.class.getDeclaredField("itemInactiveColor");
            itemInactiveColor.setAccessible(true);
            itemInactiveColor.set(this, ContextCompat.getColor(context, R.color.black));
        } catch (IllegalAccessException e) {
            ExceptionTracker.trackException(e);
        } catch (NoSuchFieldException e) {
            ExceptionTracker.trackException(e);
        }
    }
}
