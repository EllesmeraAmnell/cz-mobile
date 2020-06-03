package com.example.czechtrainer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class LoginFragment extends Fragment {

    private static final String TAG = "LoginUI";

    TextView txtString;
    EditText edittext1;
    EditText edittext2;

    LoginActivity loginActivity;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        loginActivity = (LoginActivity) getActivity();
        edittext1 = (EditText) rootView.findViewById(R.id.et_email);
        edittext2 = (EditText) rootView.findViewById(R.id.et_password);
        Button buttonLogin = (Button) rootView.findViewById(R.id.btn_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = edittext1.getText().toString();
                String password = edittext2.getText().toString();

                String postBody = "";
                try {
                    postBody = Requester.formJSONBody(
                            Requester.RequesterConsts.c_strLogin,
                            login,
                            password);
                } catch (Exception e) {
                    // Ругаем внизу
                    e.printStackTrace();
                }

                String strTargetURL = Requester.RequesterConsts.c_strURL + Requester.RequesterConsts.c_strLogin;
                String result = "";
                try {
                     result = new TaskProcessRequest().execute(
                            strTargetURL,
                            postBody,
                            Requester.RequesterConsts.c_strMethodPost).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, result);
            }
        });

        return rootView;
    }


    class TaskProcessRequest extends AsyncTask<String, Void, String> {

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
            Log.d(TAG, "Result: " + result);
            if (result.contains("message")) {
                Toast.makeText(getActivity(), "Не удалось войти", Toast.LENGTH_LONG).show();
            }
            else {
                loginActivity.SwitchToMain();
            }
        }
    }

    // TODO: потом перенести в фрагмент квиза
    // в onPostExecute должна быть перерисовка всякой дичи для квиза
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
        }
    }


}