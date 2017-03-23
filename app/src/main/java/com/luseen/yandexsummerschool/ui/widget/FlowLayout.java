package com.luseen.yandexsummerschool.ui.widget;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luseen.yandexsummerschool.R;

import java.util.List;

/**
 * Created by Chatikyan on 09.11.2016.
 */

public class FlowLayout extends ViewGroup {

    private Context context;
    private int mHeight;

    public FlowLayout(Context context) {
        super(context);
        this.context = context;
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        final int count = getChildCount();
        int xPosition = getPaddingLeft();
        int yPosition = getPaddingTop();
        int childHeightMeasureSpec;
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST)
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);
        else
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        mHeight = 0;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), childHeightMeasureSpec);
                final int childWidth = child.getMeasuredWidth();
                mHeight = Math.max(mHeight, child.getMeasuredHeight());
                if (xPosition + childWidth > width) {
                    xPosition = getPaddingLeft();
                    yPosition += mHeight;
                }
                xPosition += childWidth;
            }
        }
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            height = yPosition + mHeight;
        } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            if (yPosition + mHeight < height) {
                height = yPosition + mHeight;
            }
        }
        height += 5;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = r - l;
        int xPosition = getPaddingLeft();
        int yPosition = getPaddingTop();
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final int childMeasuredWidth = child.getMeasuredWidth();
                final int childMeasuredHeight = child.getMeasuredHeight();
                if (xPosition + childMeasuredWidth > width) {
                    xPosition = getPaddingLeft();
                    yPosition += mHeight;
                }
                child.layout(xPosition, yPosition, xPosition + childMeasuredWidth, yPosition + childMeasuredHeight);
                xPosition += childMeasuredWidth;
            }
        }
    }

    public void addFlowItems(List<SpannableStringBuilder> spannableStringBuilderList) {
        for (SpannableStringBuilder spannableStringBuilder : spannableStringBuilderList) {
            TextView textView = new TextView(context);
            int tagHorizontalPadding = (int) getResources().getDimension(R.dimen.tag_horizontal_padding);
            int tagVerticalPadding = (int) getResources().getDimension(R.dimen.tag_vertical_padding);
            //textView.setPadding(tagHorizontalPadding, tagVerticalPadding, tagHorizontalPadding, tagVerticalPadding);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView.setText(spannableStringBuilder);
            addView(textView);
        }
    }
}
