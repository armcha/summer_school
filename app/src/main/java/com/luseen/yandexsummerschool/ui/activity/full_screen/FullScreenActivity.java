package com.luseen.yandexsummerschool.ui.activity.full_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.dummy.DummyActivity;
import com.luseen.yandexsummerschool.ui.widget.FontTextView;

import butterknife.BindView;
import butterknife.OnClick;

public class FullScreenActivity extends DummyActivity {

    public static final String TRANSLATED_TEXT_INTENT_KEY = "translated_text_intent_key";

    @BindView(R.id.translated_text)
    FontTextView translatedTextView;

    public static void start(Context context, String translatedText) {
        Intent starter = new Intent(context, FullScreenActivity.class);
        starter.putExtra(TRANSLATED_TEXT_INTENT_KEY, translatedText);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        String translatedText = getIntent().getStringExtra(TRANSLATED_TEXT_INTENT_KEY);
        translatedTextView.setText(translatedText);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        enableExitAnimation();
    }

    @OnClick(R.id.close_button)
    public void onClosePressed() {
        onBackPressed();
    }
}
