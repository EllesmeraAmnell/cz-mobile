package com.example.czechtrainer;

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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;


public class LoginFragment extends Fragment {

    OkHttpClient client = new OkHttpClient();
    TextView txtString;
    EditText edittext1;
    EditText edittext2;

    public String url = "http://194.67.90.186/api/login";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        edittext1 = (EditText) rootView.findViewById(R.id.et_email);
        edittext2 = (EditText) rootView.findViewById(R.id.et_password);
        Button buttonLogin = (Button) rootView.findViewById(R.id.btn_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = edittext1.getText().toString();
                String password = edittext2.getText().toString();


                String postBody = "{\"login\":\"" + login + "\"," + "\"password\":\"" + password + "\"}";
                String res="";

                new classPostRequest().execute(url, postBody);
//                try {
//                     res = postRequest(url, postBody);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

              //  Toast.makeText(getActivity(), String.valueOf(res), Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

    public String postRequest(String targetURL, String postBody) throws Exception
    {
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("Content-Type",
                    "application/json");


            connection.setUseCaches(false);
            connection.setDoOutput(true);


            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(postBody);
            wr.close();

            //Get Response
            int status = connection.getResponseCode();
            if(status != 400 && status != 500)
            {
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                return response.toString();
            }
            else
            {
                InputStream errorStream = connection.getErrorStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(errorStream));
                StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    class classPostRequest extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strParams) {
            String res = "";
            try {
                res = postRequest(strParams[0], strParams[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getActivity(), String.valueOf(result), Toast.LENGTH_LONG).show();
        }
    }


}