package com.luseen.yandexsummerschool.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.model.dictionary.Definition;
import com.luseen.yandexsummerschool.model.dictionary.Dictionary;
import com.luseen.yandexsummerschool.model.dictionary.DictionaryTranslation;
import com.luseen.yandexsummerschool.model.dictionary.Example;
import com.luseen.yandexsummerschool.model.dictionary.Synonym;
import com.luseen.yandexsummerschool.model.dictionary.TranslatedString;
import com.luseen.yandexsummerschool.utils.ExceptionTracker;
import com.luseen.yandexsummerschool.utils.FontUtils;
import com.luseen.yandexsummerschool.utils.StringUtils;
import com.luseen.yandexsummerschool.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.VERTICAL;
import static com.luseen.yandexsummerschool.utils.AppConstants.SANS_LIGHT;

/**
 * Created by Chatikyan on 22.03.2017.
 */

public class DictionaryView extends ScrollView implements Viewable {

    private static final float RELATIVE_SPAN_PROPORTION = 0.7f;
    private static final float DEFAULT_TEXT_SIZE_IN_SP = 18f;
    private static final int MAX_ON_DIGIT_NUMBER = 9;
    private static final float LINE_SPACING = 5f;
    private int blue = R.color.blue;
    private int brown = R.color.brown;
    private int gray = R.color.gray;
    private int black = R.color.black;

    private LinearLayout linearLayout;

    public DictionaryView(Context context) {
        super(context);
        init(context);
    }

    public DictionaryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        int dictSidesPadding = (int) getResources().getDimension(R.dimen.dict_view_sides_padding);
        int dictBottomPadding = (int) getResources().getDimension(R.dimen.dict_view_bottom_padding);
        linearLayout.setPadding(dictSidesPadding, 0, dictSidesPadding, dictBottomPadding);
    }

    @Override
    public void init(Context context) {
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(VERTICAL);
        addView(linearLayout);
    }

    //Let the magic to be...
    private void buildDictionary(Dictionary dictionary) {
        //Removing all views before adding any new view
        linearLayout.removeAllViews();
        Context context = getContext();

        try {
            int definitionSize = dictionary.getDefinition().size();
            for (int z = 0; z < definitionSize; z++) {
                Definition definition = dictionary.getDefinition().get(z);
                SpannableStringBuilder definitionBuilder = new SpannableStringBuilder();
                //Making original word and transcription only once
                boolean isFirstItem = z == 0;
                boolean hasTranscription = !definition.getTranscription().isEmpty();
                if (isFirstItem) {
                    definitionBuilder.append(definition.getWord());
                    if (hasTranscription) {
                        definitionBuilder.append(StringUtils.makeColorSpan(" [", gray));
                        definitionBuilder.append(StringUtils.makeColorSpan(definition.getTranscription(), gray));
                        definitionBuilder.append(StringUtils.makeColorSpan("]\n", gray));
                    } else {
                        definitionBuilder.append("\n");
                    }
                }
                //Part of speech
                definitionBuilder.append(StringUtils.makeColoredItalicWithSize(definition.getPartOfSpeech(),
                        brown, RELATIVE_SPAN_PROPORTION));

                //Building Text view for each Definition
                FontTextView definitionTextView = baseTextView(black);
                definitionTextView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        LINE_SPACING, getResources().getDisplayMetrics()), 1.0f);
                definitionTextView.setText(definitionBuilder);
                linearLayout.addView(definitionTextView);

                //Second part is building word translations
                int translationListSize = definition.getTranslations().size();
                for (int i = 0; i < translationListSize; i++) {
                    DictionaryTranslation translation = definition.getTranslations().get(i);
                    SpannableStringBuilder translationBuilder = new SpannableStringBuilder();
                    FrameLayout numberAndFlowContainer = new FrameLayout(context);

                    //Making current number of translation if grater than one
                    int index = i + 1;
                    boolean isIndexDoubleDigit = index > MAX_ON_DIGIT_NUMBER;
                    if (translationListSize > 1) {
                        String translationIndex = String.valueOf(index);
                        FontTextView numberTextView = baseTextView(gray);
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
                        Synonym synonym = translation.getSynonyms().get(i1);
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
                    linearLayout.addView(numberAndFlowContainer);

                    //Last part is building meanings and examples
                    List<TranslatedString> meaningList = translation.getMeanings();
                    int meaningListSize = meaningList.size();
                    //Checking if has any meanings
                    boolean hasMeanings = meaningListSize > 0;
                    if (hasMeanings) {
                        FontTextView meaningTextView = baseTextView(brown);
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
                        linearLayout.addView(meaningTextView);
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
                            Example example = translation.getExamples().get(i1);
                            exampleBuilder.append(example.getWord());
                            exampleBuilder.append(" - ");
                            for (TranslatedString translatedString : example.getExampleTranslations()) {
                                exampleBuilder.append(translatedString.getText());
                            }
                            //If it is't the last one, than adding new line
                            boolean isLastItem = translation.getExamples().size() - 1 == i1;
                            if (!isLastItem)
                                exampleBuilder.append("\n");
                        }
                        //Checking if has example, and building text view
                        FontTextView examplesTextView = baseTextView(blue);
                        examplesTextView.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                        linearLayout.addView(examplesTextView);
                        //in dp, left margin from parent
                        ViewUtils.setViewMargins(examplesTextView, new int[]{40, 0, 0, 0});
                        examplesTextView.setText(exampleBuilder);
                    }
                }
            }
        } catch (Throwable throwable) {
            ExceptionTracker.trackException(throwable);
        }
    }

    private FontTextView baseTextView(int textColor) {
        Context context = getContext();
        FontTextView baseTextView = new FontTextView(context);
        baseTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE_IN_SP);
        baseTextView.setTextColor(ContextCompat.getColor(context, textColor));
        baseTextView.setTypeface(FontUtils.get(context,SANS_LIGHT));
        return baseTextView;
    }

    public void updateDictionary(Dictionary dictionary) {
        buildDictionary(dictionary);
    }

    public void reset() {
        linearLayout.removeAllViews();
    }
}
