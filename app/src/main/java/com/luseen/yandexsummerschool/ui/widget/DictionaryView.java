package com.luseen.yandexsummerschool.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.model.Dictionary;
import com.luseen.yandexsummerschool.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chatikyan on 22.03.2017.
 */

@SuppressLint("ViewConstructor")
public class DictionaryView extends LinearLayout implements Viewable {

    private int blue = R.color.blue;
    private int brown = R.color.brown;
    private int gray = R.color.gray;

    public DictionaryView(Context context) {
        super(context);
        init(context);
    }

    public DictionaryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public void init(Context context) {
        setOrientation(VERTICAL);
    }

    private void buildDictionary(Dictionary dictionary) {
        //Removing all views before adding any
        removeAllViews();
        Context context = getContext();

        int definitionSize = dictionary.getDefinition().size();
        for (int z = 0; z < definitionSize; z++) {
            Dictionary.Definition definition = dictionary.getDefinition().get(z);
            SpannableStringBuilder definitionBuilder = new SpannableStringBuilder();
            //Making original word and transcription only once
            if (z == 0) {
                definitionBuilder.append(definition.getWord());
                definitionBuilder.append(" ");
                definitionBuilder.append(StringUtils.makeColorSpan("[", gray));
                definitionBuilder.append(StringUtils.makeColorSpan(definition.getTranscription(), gray));
                definitionBuilder.append(StringUtils.makeColorSpan("]", gray));
                definitionBuilder.append(" \n");
            }
            //Part of speech
            definitionBuilder.append(StringUtils.makeColoredItalic(definition.getPartOfSpeech(), brown));
            //Building Text view for each Definition
            TextView definitionTextView = new TextView(context);
            definitionTextView.setTextColor(Color.BLACK);
            definitionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            definitionTextView.setText(definitionBuilder);
            addView(definitionTextView);

            //Second part is building word translations
            int translationListSize = definition.getTranslations().size();
            for (int i = 0; i < translationListSize; i++) {
                Dictionary.Translation translation = definition.getTranslations().get(i);
                SpannableStringBuilder translationBuilder = new SpannableStringBuilder();
                //Making current number of translation if grater than one
                if (translationListSize > 1) {
                    String translationIndex = String.valueOf(i + 1);
                    translationBuilder.append(StringUtils.makeColorSpan(translationIndex, gray));
                }
                //Adding translation word and gen
                translationBuilder.append(" ");
                translationBuilder.append(StringUtils.makeColorSpan(translation.getWord(), blue));
                translationBuilder.append(" ");
                translationBuilder.append(StringUtils.makeColoredItalic(translation.getGen(), gray));
                int synonymsSize = translation.getSynonyms().size();
                boolean hasSynonyms = synonymsSize > 0;
                if (hasSynonyms)
                    translationBuilder.append(StringUtils.makeColorSpan(",", blue));

                //There we need list of words to give to flow layout
                List<SpannableStringBuilder> spannableStringBuilderList = new ArrayList<>();
                spannableStringBuilderList.add(translationBuilder);
                for (int i1 = 0; i1 < translation.getSynonyms().size(); i1++) {
                    Dictionary.Synonym synonym = translation.getSynonyms().get(i1);
                    SpannableStringBuilder flowItemBuilder = new SpannableStringBuilder();
                    flowItemBuilder.append(" ");
                    flowItemBuilder.append(StringUtils.makeColorSpan(synonym.getWord(), blue));
                    flowItemBuilder.append(" ");
                    flowItemBuilder.append(StringUtils.makeColoredItalic(synonym.getGen(), gray));
                    //We don't need append "," if it is the last one
                    if (translation.getSynonyms().size() - 1 != i1)
                        flowItemBuilder.append(StringUtils.makeColorSpan(",", blue));
                    spannableStringBuilderList.add(flowItemBuilder);
                }

                //Creating flow layout for each translation
                FlowLayout flowLayout = new FlowLayout(context);
                flowLayout.addFlowItems(spannableStringBuilderList);
                spannableStringBuilderList.clear();
                addView(flowLayout);

                //Last part is building meanings and examples
                List<Dictionary.TranslatedString> meaningList = translation.getMeanings();
                int meaningListSize = meaningList.size();
                SpannableStringBuilder meanAndExampleBuilder = new SpannableStringBuilder();
                //Checking if has any meanings, to add "("
                boolean hasMeanings = meaningListSize > 0;
                if (hasMeanings) {
                    meanAndExampleBuilder.append(StringUtils.makeColorSpan("(", brown));
                }
                //this is so funny c++
                for (int c = 0; c < meaningListSize; c++) {
                    meanAndExampleBuilder.append(StringUtils.makeColorSpan(meaningList.get(c).getText(), brown));
                    //If the meaning is't the last one, adding ","
                    boolean isLastItem = meaningListSize - 1 == c;
                    if (!isLastItem) {
                        meanAndExampleBuilder.append(StringUtils.makeColorSpan(",", brown));
                        meanAndExampleBuilder.append(" ");
                    }
                }
                //Checking if has any meanings, to add ")"
                if (hasMeanings) {
                    meanAndExampleBuilder.append(StringUtils.makeColorSpan(")", brown));
                }
                int examplesSize = translation.getExamples().size();
                //Adding examples
                boolean hasExamples = examplesSize > 0;
                if (hasExamples) {
                    meanAndExampleBuilder.append(StringUtils.makeColorSpan("\n", brown));
                }
                for (int i1 = 0; i1 < translation.getExamples().size(); i1++) {
                    Dictionary.Example example = translation.getExamples().get(i1);
                    meanAndExampleBuilder.append(StringUtils.makeColoredItalic(example.getWord(), blue));
                    meanAndExampleBuilder.append(" ");
                    meanAndExampleBuilder.append(StringUtils.makeColoredItalic("-", blue));
                    meanAndExampleBuilder.append(" ");

                    for (Dictionary.TranslatedString translatedString : example.getExampleTranslations()) {
                        meanAndExampleBuilder.append(StringUtils.makeColoredItalic(translatedString.getText(), blue));
                    }
                    boolean isLastItem = translation.getExamples().size() - 1 == i1;
                    //If it is't the last one, than adding new line
                    if (!isLastItem)
                        meanAndExampleBuilder.append("\n");
                }
                //Checking if has meaning or example, and building text view
                boolean hasMeanOrExample = !meanAndExampleBuilder.toString().isEmpty();
                if (hasMeanOrExample) {
                    TextView meanAndExampleTextView = new TextView(context);
                    meanAndExampleTextView.setTextColor(Color.BLACK);
                    meanAndExampleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    addView(meanAndExampleTextView);
                    meanAndExampleTextView.setText(meanAndExampleBuilder);
                }
            }
        }
    }

    public void updateDictionary(Dictionary dictionary) {
        buildDictionary(dictionary);
    }

    public void reset() {
        removeAllViews();
    }
}
