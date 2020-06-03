package com.example.czechtrainer.ui.words;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.czechtrainer.R;

public class WordsFragment extends Fragment {

    private WordsViewModel wordsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        wordsViewModel =
                ViewModelProviders.of(this).get(WordsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_words, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        wordsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}