package com.luseen.yandexsummerschool.model;

/**
 * Created by Chatikyan on 30.03.2017.
 */

public enum YaError {

    // TODO: 31.03.2017 get names from yandex api doc
    LANGUAGE_IS_NOT_SUPPORTED(501),
    INVALID_API_KEY(401),
    BLOCKED_API_KEY(402),
    EXCEEDED_THE_DAILY_LIMIT(404),
    EXCEEDED_THE_MAXIMUM_TEXT_SIZE(413),
    THE_TEXT_CANNOT_BE_TRANSLATED(422),
    UNKNOWN(-1);

    int errorCode;

    YaError(int code) {
        this.errorCode = code;
    }

    public static YaError getErrorByCode(int code) {
        for (int i = 0; i < values().length; i++) {
            YaError yaError = values()[i];
            if (code == yaError.errorCode) {
                return yaError;
            }
        }
        return UNKNOWN;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
