package com.luseen.yandexsummerschool.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luseen.yandexsummerschool.R;
import com.luseen.yandexsummerschool.base_mvp.dummy.DummyFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TranslationFragment extends DummyFragment {


    public TranslationFragment() {
        // Required empty public constructor
    }

    public static TranslationFragment newInstance() {

        Bundle args = new Bundle();
        TranslationFragment fragment = new TranslationFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_translation, container, false);
    }

}
