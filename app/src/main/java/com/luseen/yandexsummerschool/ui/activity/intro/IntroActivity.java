package com.luseen.yandexsummerschool.ui.activity.intro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.luseen.verticalintrolibrary.VerticalIntro;
import com.luseen.verticalintrolibrary.VerticalIntroItem;
import com.luseen.yandexsummerschool.App;
import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.ui.activity.root.RootActivity;

public class IntroActivity extends VerticalIntro {

    public static final String INTRO_STATE_KEY = "intro_state_key";

    private int[] titles = {R.string.intro_first_title, R.string.intro_second_title, R.string.intro_third_title};
    private int[] texts = {R.string.intro_first_text, R.string.intro_second_text, R.string.intro_third_text};
    private int[] colors = {R.color.intro_first, R.color.intro_second, R.color.intro_third};
    private int[] images = {R.drawable.intro_first, R.drawable.intro_second, R.drawable.intro_third};

    public static void start(Context context) {
        Intent starter = new Intent(context, IntroActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void init() {
        for (int i = 0; i < titles.length; i++) {
            VerticalIntroItem verticalIntroItem = new VerticalIntroItem.Builder()
                    .backgroundColor(colors[i])
                    .image(images[i])
                    .title(getString(titles[i]))
                    .text(getString(texts[i]))
                    .build();
            addIntroItem(verticalIntroItem);
        }

        setNextText(getString(R.string.intro_next));
        setDoneText(getString(R.string.intro_done));
        setSkipText(getString(R.string.intro_skip));
    }

    @Override
    protected Integer setLastItemBottomViewColor() {
        return R.color.yandex_yellow;
    }

    @Override
    protected void onSkipPressed(View view) {

    }

    @Override
    protected void onFragmentChanged(int position) {

    }

    @Override
    protected void onDonePressed() {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences();
        sharedPreferences.edit().putBoolean(INTRO_STATE_KEY, true).apply();

        RootActivity.start(this);
        finish();
        overridePendingTransition(R.anim.slide_in_start, R.anim.slide_in_finish);
    }
}
