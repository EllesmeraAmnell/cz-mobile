package com.example.czechtrainer.ui.profile;

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

import com.example.czechtrainer.LoginActivity;
import com.example.czechtrainer.MainActivity;
import com.example.czechtrainer.R;
import com.example.czechtrainer.Requester;
import com.example.czechtrainer.models.ProfileObjectModel;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileUI";

    MainActivity mainActivity;
    ProfileObjectModel profileObjectModel;

    TextView textViewEmail;
    TextView textViewLogin;
    TextView textViewName;
    TextView textViewSurname;
    TextView textViewRole;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        mainActivity = (MainActivity) getActivity();
        textViewEmail = root.findViewById(R.id.textViewEmail);
        textViewLogin = root.findViewById(R.id.textViewLogin);
        textViewName = root.findViewById(R.id.textViewName);
        textViewSurname = root.findViewById(R.id.textViewSurname);
        textViewRole = root.findViewById(R.id.textViewRole);
        performProfileRequest(mainActivity.account);
        assignButtonListeners(root);
        return root;
    }

    private void assignButtonListeners(View root) {
        final Button buttonLogout = (Button) root.findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainActivity.SwitchToLogin();
            }
        });
    }

    private void performProfileRequest(String account) {
        String postBody = "";
        try {
            postBody = Requester.formJSONBody(
                    Requester.RequesterConsts.c_strProfile,
                    account);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String strTargetURL = Requester.RequesterConsts.c_strURL + Requester.RequesterConsts.c_strProfile;
        String result = "";
        try {
            result = new ProfileFragment.ProfileRequest().execute(
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

    class ProfileRequest extends AsyncTask<String, Void, String> {

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
                profileObjectModel = new ProfileObjectModel(result);
                updateControlsText();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateControlsText() {
        textViewEmail.setText(profileObjectModel.email);
        textViewLogin.setText(profileObjectModel.login);
        textViewName.setText(profileObjectModel.name);
        textViewSurname.setText(profileObjectModel.surname);
        textViewRole.setText(profileObjectModel.role);
    }
}