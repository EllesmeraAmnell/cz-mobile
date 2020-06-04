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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_words, container, false);

//        int BOOKSHELF_ROWS = 5;
//        int BOOKSHELF_COLUMNS = 5;
//
//        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
//
//        for (int i = 0; i < BOOKSHELF_ROWS; i++) {
//
//            TableRow tableRow = new TableRow(this);
//            tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//                    LayoutParams.WRAP_CONTENT));
//            tableRow.setBackgroundResource(R.drawable.shelf);
//
//            for (int j = 0; j < BOOKSHELF_COLUMNS; j++) {
//                ImageView imageView = new ImageView(this);
//                imageView.setImageResource(R.drawable.book);
//
//                tableRow.addView(imageView, j);
//            }
//
//            tableLayout.addView(tableRow, i);
//        }

        return root;
    }
}