package com.example.czechtrainer.ui.quiz;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.czechtrainer.R;
import com.example.czechtrainer.Requester;
import com.example.czechtrainer.models.QuizObjectModel;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class QuizFragment extends Fragment {

    private static final String TAG = "QuizUI";

    private QuizObjectModel quizObjectModel;
    private TextView textQuestion;
    private Button[] buttonOptions = new Button[4];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_quiz, container, false);

        assignButtonListeners(root);
        performQuizRequest();

        return root;
    }

    private void assignButtonListeners(View root){
        textQuestion = root.findViewById(R.id.text_question);
        buttonOptions[0] = root.findViewById(R.id.button1);
        buttonOptions[1] = root.findViewById(R.id.button2);
        buttonOptions[2] = root.findViewById(R.id.button3);
        buttonOptions[3] = root.findViewById(R.id.button4);
        final Button buttonNext = (Button) root.findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (Button buttonOption : buttonOptions) {
                    buttonOption.setBackgroundResource(R.drawable.et_custom);
                }
                performQuizRequest();
            }
        });

        View.OnClickListener optionClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                String answer = quizObjectModel.answer;
                for (Button buttonOption : buttonOptions) {
                    if (buttonOption.getText().equals(answer)){
                        buttonOption.setBackgroundResource(R.drawable.btn_green);
                    }
                    else{
                        buttonOption.setBackgroundResource(R.drawable.btn_red);
                    }
                }
            }
        };

        for (Button buttonOption : buttonOptions) {
            buttonOption.setOnClickListener(optionClickListener);
        }
    }

    private void performQuizRequest() {
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
                quizObjectModel = new QuizObjectModel(result);
                updateControlsText();
            } catch (JSONException e) {
                Log.e(TAG, "Got exception" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void updateControlsText() {
        textQuestion.setText(quizObjectModel.question);
        for (int i = 0; i < buttonOptions.length; i++) {
            buttonOptions[i].setText(quizObjectModel.options[i]);
        }
    }
}