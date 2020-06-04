package com.example.czechtrainer.ui.words;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.czechtrainer.R;

public class WordsFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_words, container, false);

//        int BOOKSHELF_ROWS = 5;
//        int BOOKSHELF_COLUMNS = 5;
//
//        TableLayout tableLayout = (TableLayout) root.findViewById(R.id.tableLayout);
//
//        for (int i = 0; i < BOOKSHELF_ROWS; i++) {
//
//            TableRow tableRow = new TableRow(getActivity());
//            tableRow.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT));
//            tableRow.setBackgroundResource(R.drawable.et_custom);
//
//            for (int j = 0; j < BOOKSHELF_COLUMNS; j++) {
//                TextView textView = (TextView)root.findViewById(R.id.section_bp_label);
//                textView.setText("123qwe");
//                tableRow.addView(textView);
//            }
//
//            tableLayout.addView(tableRow, i);
//        }

        return root;
    }
}