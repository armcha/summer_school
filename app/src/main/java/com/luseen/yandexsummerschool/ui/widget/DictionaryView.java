package com.luseen.yandexsummerschool.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.model.Dictionary;
import com.luseen.yandexsummerschool.utils.StringUtils;

import java.util.List;

/**
 * Created by Chatikyan on 22.03.2017.
 */

@SuppressLint("ViewConstructor")
public class DictionaryView extends LinearLayout implements Viewable {

    private Dictionary dictionary;
    private int blue = R.color.blue;
    private int brown = R.color.brown;
    private int gray = R.color.gray;

    public DictionaryView(Context context, Dictionary dictionary) {
        super(context);
        this.dictionary = dictionary;
        init(context);
    }

    @Override
    public void init(Context context) {
        TextView textView = new TextView(context);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        addView(textView);

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        int definitionSize = dictionary.getDefinition().size();
        for (int z = 0; z < definitionSize; z++) {
            Dictionary.Definition definition = dictionary.getDefinition().get(z);
            if (z == 0) {
                stringBuilder.append(definition.getWord());
                stringBuilder.append(" ");
                stringBuilder.append(StringUtils.makeColorSpan("[", gray));
                stringBuilder.append(StringUtils.makeColorSpan(definition.getTranscription(), gray));
                stringBuilder.append(StringUtils.makeColorSpan("]", gray));
            }
            stringBuilder.append("\n");
            stringBuilder.append(StringUtils.makeColoredItalic(definition.getPartOfSpeech(), brown));
            int translationListSize = definition.getTranslations().size();
            for (int i = 0; i < translationListSize; i++) {
                Dictionary.Translation translation = definition.getTranslations().get(i);
                stringBuilder.append("\n");
                if (translationListSize > 1) {
                    String translationIndex = String.valueOf(i + 1);
                    stringBuilder.append(StringUtils.makeColorSpan(translationIndex, gray));
                }
                stringBuilder.append(" ");
                stringBuilder.append(StringUtils.makeColorSpan(translation.getWord(), blue));
                stringBuilder.append(" ");
                stringBuilder.append(StringUtils.makeColoredItalic(translation.getGen(), gray));
                for (Dictionary.Synonym synonym : translation.getSynonyms()) {
                    stringBuilder.append(StringUtils.makeColorSpan(",", R.color.blue));
                    stringBuilder.append(" ");
                    stringBuilder.append(StringUtils.makeColorSpan(synonym.getWord(), blue));
                    stringBuilder.append(" ");
                    stringBuilder.append(StringUtils.makeColoredItalic(synonym.getGen(), gray));
                }
                List<Dictionary.TranslatedString> meaningList = translation.getMeanings();
                int meaningListSize = meaningList.size();
                if (meaningListSize > 0) {
                    stringBuilder.append("\n");
                    stringBuilder.append(StringUtils.makeColorSpan("(", brown));
                }
                //this is so funny c++
                for (int c = 0; c < meaningListSize; c++) {
                    stringBuilder.append(StringUtils.makeColorSpan(meaningList.get(c).getText(), brown));
                    //The last one
                    if (meaningListSize - 1 != c) {
                        stringBuilder.append(StringUtils.makeColorSpan(",", brown));
                        stringBuilder.append(" ");
                    }
                }
                if (meaningListSize > 0) {
                    stringBuilder.append(StringUtils.makeColorSpan(")", brown));
                }
                for (Dictionary.Example example : translation.getExamples()) {
                    stringBuilder.append("\n");
                    stringBuilder.append(StringUtils.makeColoredItalic(example.getWord(), blue));
                    stringBuilder.append(" ");
                    stringBuilder.append(StringUtils.makeColoredItalic("-", blue));
                    stringBuilder.append(" ");
                    for (Dictionary.TranslatedString translatedString : example.getExampleTranslations()) {
                        stringBuilder.append(StringUtils.makeColoredItalic(translatedString.getText(), blue));
                    }
                }
            }
        }
        textView.setText(stringBuilder);
    }
}
