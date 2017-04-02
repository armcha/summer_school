package com.luseen.yandexsummerschool.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.luseen.yandexsummerschool.model.ErrorParser;
import com.luseen.yandexsummerschool.model.YaError;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by Chatikyan on 31.03.2017.
 */

public class HttpUtils {

    private HttpUtils() {
        throw new RuntimeException("Private constructor cannot be accessed");
    }

    public static YaError getYaError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException exception = ((HttpException) throwable);
            ResponseBody body = exception.response().errorBody();
            Gson gson = new Gson();
            TypeAdapter<ErrorParser> adapter = gson.getAdapter(ErrorParser.class);
            try {
                ErrorParser errorParser = adapter.fromJson(body.string());
                return YaError.getErrorByCode(errorParser.getCode());
            } catch (IOException e) {
                return YaError.UNKNOWN;
            }
        }
        return YaError.UNKNOWN;
    }
}
