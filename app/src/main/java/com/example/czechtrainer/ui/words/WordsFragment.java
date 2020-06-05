package com.example.czechtrainer.ui.words;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.example.czechtrainer.Requester;
import com.example.czechtrainer.models.QuizObjectModel;
import com.example.czechtrainer.models.WordsObjectModel;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class WordsFragment extends Fragment {

    private static final String TAG = "WordsUI";

    WordsObjectModel wordsObjectModel;
    TableLayout tableLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_words, container, false);
        tableLayout = (TableLayout) root.findViewById(R.id.table);
        performDictRequest();
        return root;
    }

    private void performDictRequest() {
        String postBody = "";
        try {
            postBody = Requester.formJSONBody(
                    Requester.RequesterConsts.c_strWords);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String strTargetURL = Requester.RequesterConsts.c_strURL + Requester.RequesterConsts.c_strWords;
        String result = "";
        try {
            result = new DictRequest().execute(
                    strTargetURL,
                    postBody,
                    Requester.RequesterConsts.c_strMethodGet).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, result);
    }

    class DictRequest extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strParams) {
            String res = "";
            try {
                res = Requester.execRequest(strParams[0], strParams[1], strParams[2]);
            } catch (Exception e) {
                Log.e(TAG, "Got exception" + e.getMessage());
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                wordsObjectModel = new WordsObjectModel(result);
                updateControlsText();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateControlsText() {
        Log.i(TAG, wordsObjectModel.count.toString());
        WordsObjectModel.WordObject[] wordObjects = wordsObjectModel.words;
        for (int i = 0; i < wordsObjectModel.count; i++) {
            addRow(i + 1, wordObjects[i]);
        }
    }

    public void addRow(int i, WordsObjectModel.WordObject object) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        TableRow tr = (TableRow) inflater.inflate(R.layout.table_row, null);
        TextView tv = (TextView) tr.findViewById(R.id.colNo);
        tv.setText(Integer.toString(i) + " ");
        tv = (TextView) tr.findViewById(R.id.colRuWord);
        tv.setText(object.rus);
        tv = (TextView) tr.findViewById(R.id.colCzWord);
        tv.setText(object.cz);
        tv = (TextView) tr.findViewById(R.id.colPartOfSpeech);
        tv.setText(object.part_of_speech);
        tv = (TextView) tr.findViewById(R.id.colGender);
        tv.setText(object.gender);
        tv = (TextView) tr.findViewById(R.id.colForm);
        tv.setText(object.form);

        tableLayout.addView(tr); //добавляем созданную строку в таблицу
    }
}