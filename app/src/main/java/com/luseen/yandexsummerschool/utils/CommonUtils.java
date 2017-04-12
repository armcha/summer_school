package com.luseen.yandexsummerschool.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.net.Uri;
import android.os.Build;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.v4.content.ContextCompat;

import com.luseen.yandexsummerschool.R;

import static com.luseen.yandexsummerschool.utils.AppConstants.CHROME_PACKAGE_NAME;

/**
 * Created by Chatikyan on 18.03.2017.
 */

public class CommonUtils {

    private CommonUtils() {
        throw new RuntimeException("Private constructor cannot be accessed");
    }

    public static boolean isLollipopOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isMarshmallowOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static void openUrlWithCustomTab(String url, Activity context) {
        CustomTabsServiceConnection mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName,
                                                     CustomTabsClient customTabsClient) {
                customTabsClient.warmup(0L);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };

        CustomTabsClient.bindCustomTabsService(context, CHROME_PACKAGE_NAME, mCustomTabsServiceConnection);
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder(null)
                .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setStartAnimations(context, R.anim.slide_in_start, R.anim.slide_in_finish)
                .setExitAnimations(context, R.anim.slide_out_start, R.anim.slide_out_finish)
                .setShowTitle(true)
                .addDefaultShareMenuItem()
                .build();

        customTabsIntent.launchUrl(context, Uri.parse(url));
    }
}
