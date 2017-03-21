package com.luseen.yandexsummerschool.utils;

import android.util.Log;

import com.luseen.yandexsummerschool.BuildConfig;

public class Logger {

    private Logger() {
        throw new AssertionError("No instances");
    }

    public static final String TAG = "Logger";
    private static boolean isLoggable = BuildConfig.DEBUG;

    public static void e(Object message) {
        if (isLoggable) {
            Log.e(TAG, "| " + makeLog(message.toString()));
        }
    }

    public static void d(Object message) {
        if (isLoggable)
            Log.d(TAG, "| " + makeLog(message.toString()));
    }

    public static void log(Object message) {
        if (isLoggable) {
            if (message != null) {
                Log.e(TAG, "| " + makeLog(message.toString()));
            } else {
                Log.e(TAG, "| " + null + " |");
            }
        }
    }

    static void releaseLog(Object message) {
        if (message != null) {
            Log.e(TAG, "| " + makeLog(message.toString()));
        } else {
            Log.e(TAG, "| " + null + " |");
        }
    }

    private static String makeLog(String message) {
        StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
        int currentIndex = -1;
        for (int i = 0; i < stackTraceElement.length; i++) {
            if (stackTraceElement[i].getMethodName().compareTo("log") == 0) {
                currentIndex = i + 1;
                break;
            }
        }

        StackTraceElement traceElement = Thread.currentThread().getStackTrace()[currentIndex];
        String fullClassName = traceElement.getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = traceElement.getMethodName();
        String lineNumber = String.valueOf(traceElement.getLineNumber());
        return message + " | (" + className + ".java:" + lineNumber + ")";
    }
}
