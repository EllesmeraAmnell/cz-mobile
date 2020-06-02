package com.example.czechtrainer;

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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginFragment extends Fragment {

    OkHttpClient client = new OkHttpClient();
    TextView txtString;
    EditText edittext1;
    EditText edittext2;

    public String url = "http://localhost:4000/api/login";
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

                String postBody = "{\n" +
                        "    \"login\": \"" + login + "\",\n" +
                        "    \"password\": \"" + password + "\"\n" +
                        "}";
                //String res = postRequest(url, postBody);

                //Toast.makeText(getActivity(), String.valueOf(res), Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

    private String postRequest(String url, String postBody) throws Exception {
        throw new Exception("Http request not implemented");
    }


}