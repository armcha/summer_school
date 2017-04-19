package com.luseen.yandexsummerschool.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chatikyan on 19.04.2017.
 */

public class FontUtils {

    private FontUtils() {
        throw new RuntimeException("Private constructor cannot be accessed");
    }

    private static final Map<String, Typeface> typefaceCache = new HashMap<>();

    public static Typeface get(Context context, String font) {
        synchronized (typefaceCache) {
            if (!typefaceCache.containsKey(font)) {
                Typeface tf = Typeface.createFromAsset(
                        context.getApplicationContext().getAssets(), "fonts/" + font + ".ttf");
                typefaceCache.put(font, tf);
            }
            return typefaceCache.get(font);
        }
    }

    public static String getName(@NonNull Typeface typeface) {
        for (Map.Entry<String, Typeface> entry : typefaceCache.entrySet()) {
            if (entry.getValue() == typeface) {
                return entry.getKey();
            }
        }
        return null;
    }
}
