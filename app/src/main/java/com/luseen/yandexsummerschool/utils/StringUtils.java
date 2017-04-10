package com.luseen.yandexsummerschool.utils;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.luseen.yandexsummerschool.App;

public class StringUtils {

    private StringUtils() {
        throw new RuntimeException("Private constructor cannot be accessed");
    }

    public static final String EMPTY = "";
    public static final String SPACE = " ";

    public static CharSequence makeColorSpan(String text, int colorId) {
        SpannableString spannedText = new SpannableString(text);
        spannedText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(App.getInstance(), colorId)),
                0, spannedText.length(), 0);
        return spannedText;
    }

    public static CharSequence makeColoredItalic(String text, int colorId) {
        SpannableString spannedText = new SpannableString(text);
        spannedText.setSpan(new StyleSpan(Typeface.ITALIC), 0, spannedText.length(), 0);
        spannedText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(App.getInstance(), colorId)),
                0, spannedText.length(), 0);
        return spannedText;
    }

    public static CharSequence changeSize(String text, float proportion) {
        SpannableString spannedText = new SpannableString(text);
        spannedText.setSpan(new RelativeSizeSpan(proportion), 0, spannedText.length(), 0);
        return spannedText;
    }

    public static CharSequence makeColoredItalicWithSize(String text, int colorId, float proportion) {
        SpannableString spannedText = new SpannableString(text);
        spannedText.setSpan(new StyleSpan(Typeface.ITALIC), 0, spannedText.length(), 0);
        spannedText.setSpan(new RelativeSizeSpan(proportion), 0, spannedText.length(), 0);
        spannedText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(App.getInstance(), colorId)),
                0, spannedText.length(), 0);
        return spannedText;
    }

    public static CharSequence makeColorSpanWithSize(String text, int colorId, float proportion) {
        SpannableString spannedText = new SpannableString(text);
        spannedText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(App.getInstance(), colorId)),
                0, spannedText.length(), 0);
        spannedText.setSpan(new RelativeSizeSpan(proportion), 0, spannedText.length(), 0);
        return spannedText;
    }
}
