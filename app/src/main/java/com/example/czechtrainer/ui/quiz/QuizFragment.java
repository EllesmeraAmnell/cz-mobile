package com.example.czechtrainer.ui.quiz;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.example.czechtrainer.R;
import com.example.czechtrainer.Requester;

import java.util.concurrent.ExecutionException;

public class QuizFragment extends Fragment {

    private QuizViewModel quizViewModel;

    private static final String TAG = "QuizUI";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        quizViewModel = ViewModelProviders.of(this).get(QuizViewModel.class);
        View root = inflater.inflate(R.layout.fragment_quiz, container, false);
        final TextView textView = root.findViewById(R.id.text_question);
        quizViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        UpdateQuizContent();
        return root;
    }

    private void UpdateQuizContent() {
        String postBody = "";
        try {
            postBody = Requester.formJSONBody(
                    Requester.RequesterConsts.c_strQuiz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String strTargetURL = Requester.RequesterConsts.c_strURL + Requester.RequesterConsts.c_strQuiz;
        String result = "";
        try {
            result = new QuizRequest().execute(
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


    class QuizRequest extends AsyncTask<String, Void, String> {

        String m_strSubsystem = null;

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
            Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
        }
    }
}