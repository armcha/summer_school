package com.luseen.yandexsummerschool.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.model.Dictionary;
import com.luseen.yandexsummerschool.utils.StringUtils;
import com.luseen.yandexsummerschool.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chatikyan on 22.03.2017.
 */

public class DictionaryView extends LinearLayout implements Viewable {

    private static final float RELATIVE_SPAN_PROPORTION = 0.7f;
    private static final int MAX_ON_DIGIT_NUMBER = 9;
    private static final float LINE_SPACING = 5f;
    private int blue = R.color.blue;
    private int brown = R.color.brown;
    private int gray = R.color.gray;
    private int black = R.color.black;

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
        //Removing all views before adding any new view
        removeAllViews();
        Context context = getContext();

        int definitionSize = dictionary.getDefinition().size();
        for (int z = 0; z < definitionSize; z++) {
            Dictionary.Definition definition = dictionary.getDefinition().get(z);
            SpannableStringBuilder definitionBuilder = new SpannableStringBuilder();
            //Making original word and transcription only once
            boolean isFirstItem = z == 0;
            if (isFirstItem) {
                definitionBuilder.append(definition.getWord());
                definitionBuilder.append(StringUtils.makeColorSpan(" [", gray));
                definitionBuilder.append(StringUtils.makeColorSpan(definition.getTranscription(), gray));
                definitionBuilder.append(StringUtils.makeColorSpan("]\n", gray));
            }
            //Part of speech
            definitionBuilder.append(StringUtils.makeColoredItalicWithSize(definition.getPartOfSpeech(),
                    brown, RELATIVE_SPAN_PROPORTION));

            //Building Text view for each Definition
            TextView definitionTextView = baseTextView(black);
            definitionTextView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    LINE_SPACING, getResources().getDisplayMetrics()), 1.0f);
            definitionTextView.setText(definitionBuilder);
            addView(definitionTextView);

            //Second part is building word translations
            int translationListSize = definition.getTranslations().size();
            for (int i = 0; i < translationListSize; i++) {
                Dictionary.Translation translation = definition.getTranslations().get(i);
                SpannableStringBuilder translationBuilder = new SpannableStringBuilder();
                FrameLayout numberAndFlowContainer = new FrameLayout(context);

                //Making current number of translation if grater than one
                int index = i + 1;
                boolean isIndexDoubleDigit = index > MAX_ON_DIGIT_NUMBER;
                if (translationListSize > 1) {
                    String translationIndex = String.valueOf(index);
                    TextView numberTextView = baseTextView(gray);
                    numberTextView.setText(StringUtils.changeSize(translationIndex, RELATIVE_SPAN_PROPORTION));
                    numberAndFlowContainer.addView(numberTextView);
                    //in dp, top margin to bring baseline to definitionTextView
                    ViewUtils.setViewMargins(numberTextView, new int[]{0, 8, 0, 0});
                }
                //Adding translation word and gen
                translationBuilder.append(StringUtils.makeColorSpan(translation.getWord(), blue));
                translationBuilder.append(" ");
                translationBuilder.append(StringUtils.makeColoredItalic(translation.getGen(), gray));
                int synonymsSize = translation.getSynonyms().size();
                boolean hasSynonyms = synonymsSize > 0;
                if (hasSynonyms) {
                    translationBuilder.append(StringUtils.makeColorSpan(", ", blue));
                }

                //There we need list of words to give to flow layout
                List<SpannableStringBuilder> spannableStringBuilderList = new ArrayList<>();
                spannableStringBuilderList.add(translationBuilder);
                for (int i1 = 0; i1 < translation.getSynonyms().size(); i1++) {
                    Dictionary.Synonym synonym = translation.getSynonyms().get(i1);
                    SpannableStringBuilder flowItemBuilder = new SpannableStringBuilder();
                    flowItemBuilder.append(StringUtils.makeColorSpan(synonym.getWord(), blue));
                    flowItemBuilder.append(" ");
                    flowItemBuilder.append(StringUtils.makeColoredItalic(synonym.getGen(), gray));
                    //We don't need append "," if it is the last one
                    boolean isLastItem = translation.getSynonyms().size() - 1 == i1;
                    if (!isLastItem) {
                        flowItemBuilder.append(StringUtils.makeColorSpan(", ", blue));
                    }
                    spannableStringBuilderList.add(flowItemBuilder);
                }

                //Creating flow layout for each translation
                FlowLayout flowLayout = new FlowLayout(context);
                flowLayout.addFlowItems(spannableStringBuilderList);
                numberAndFlowContainer.addView(flowLayout);
                //in dp left and top margin
                ViewUtils.setViewMargins(flowLayout, new int[]{isIndexDoubleDigit ? 24 : 15, 5, 0, 0});
                spannableStringBuilderList.clear();
                addView(numberAndFlowContainer);

                //Last part is building meanings and examples
                List<Dictionary.TranslatedString> meaningList = translation.getMeanings();
                int meaningListSize = meaningList.size();
                //Checking if has any meanings
                boolean hasMeanings = meaningListSize > 0;
                if (hasMeanings) {
                    TextView meaningTextView = baseTextView(brown);
                    StringBuilder meaningBuilder = new StringBuilder();
                    meaningBuilder.append("(");

                    //this is so funny c++
                    for (int c = 0; c < meaningListSize; c++) {
                        meaningBuilder.append(meaningList.get(c).getText());
                        //If the meaning is't the last one, adding ","
                        boolean isLastItem = meaningListSize - 1 == c;
                        if (!isLastItem) {
                            meaningBuilder.append(", ");
                        }
                    }
                    //Checking if has any meanings, to add ")"
                    meaningBuilder.append(")");
                    meaningTextView.setText(meaningBuilder);
                    addView(meaningTextView);
                    //in dp left margin from parent
                    ViewUtils.setViewMargins(meaningTextView, new int[]{isIndexDoubleDigit ? 24 : 15, 0, 0, 0});
                }
                //Adding examples
                int examplesSize = translation.getExamples().size();
                //Checking if has any meanings
                boolean hasExamples = examplesSize > 0;
                if (hasExamples) {
                    SpannableStringBuilder exampleBuilder = new SpannableStringBuilder();
                    for (int i1 = 0; i1 < translation.getExamples().size(); i1++) {
                        Dictionary.Example example = translation.getExamples().get(i1);
                        exampleBuilder.append(example.getWord());
                        exampleBuilder.append(" - ");
                        for (Dictionary.TranslatedString translatedString : example.getExampleTranslations()) {
                            exampleBuilder.append(translatedString.getText());
                        }
                        //If it is't the last one, than adding new line
                        boolean isLastItem = translation.getExamples().size() - 1 == i1;
                        if (!isLastItem)
                            exampleBuilder.append("\n");
                    }
                    //Checking if has example, and building text view
                    TextView examplesTextView = baseTextView(blue);
                    examplesTextView.setTypeface(null, Typeface.ITALIC);
                    examplesTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    addView(examplesTextView);
                    //in dp, left margin from parent
                    ViewUtils.setViewMargins(examplesTextView, new int[]{40, 0, 0, 0});
                    examplesTextView.setText(exampleBuilder);
                }
            }
        }
    }

    private TextView baseTextView(int textColor) {
        Context context = getContext();
        TextView baseTextView = new TextView(context);
        baseTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        baseTextView.setTextColor(ContextCompat.getColor(context, textColor));
        return baseTextView;
    }

    public void updateDictionary(Dictionary dictionary) {
        buildDictionary(dictionary);
    }

    public void reset() {
        removeAllViews();
    }
}
