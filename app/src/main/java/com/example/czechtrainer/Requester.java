package com.example.czechtrainer;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Requester {

    private static final String TAG = "REQ";

    public static interface JSONConsts {
        public static final String c_strPasswordField = "password";
        public static final String c_strLoginField = "login";
        public static final String c_strEmailField = "email";
        public static final String c_strNameField = "name";
        public static final String c_strSurnameField = "surname";
    }

    public static interface RequesterConsts {
        public static final String c_strMethodPost = "POST";
        public static final String c_strMethodGet = "GET";
        public static final String c_strMethodPut = "PUT";
        public static final String c_strMethodDelete = "DELETE";

        public static final String c_strURL = "http://194.67.90.186/api/";

        public static final String c_strLogin = "login";
        public static final String c_strSignup = "signup";
        public static final String c_strQuiz = "quiz";
        public static final String c_strLogout = "logout";
        public static final String c_strProfile = "profile";
        public static final String c_strWords = "words";
    }


    public static String formJSONBody(String... astrParams) throws Exception {
        // quiz \ login \ profile etc. - ради чего запрос
        String strSubsystems = astrParams[0];
        if (!strSubsystems.equals("words")) {
            if (strSubsystems.equals(RequesterConsts.c_strQuiz)){
                return "";
            }
            if (strSubsystems.equals(RequesterConsts.c_strProfile)){
                if (astrParams.length < 2) {
                    Log.e(TAG, "Not enough arguments for login");
                    throw new Exception("Not enough arguments for login");
                }
                JSONObject jsonBody = new JSONObject();
                jsonBody.put(JSONConsts.c_strLoginField, astrParams[1]);
                return jsonBody.toString();
            }
            if (strSubsystems.equals(RequesterConsts.c_strLogin)) {
                if (astrParams.length < 3) {
                    Log.e(TAG, "Not enough arguments for login");
                    throw new Exception("Not enough arguments for login");
                }
                JSONObject jsonBody = new JSONObject();
                jsonBody.put(JSONConsts.c_strLoginField, astrParams[1]);
                jsonBody.put(JSONConsts.c_strPasswordField, astrParams[2]);

                return jsonBody.toString();
            }
            if (strSubsystems.equals(RequesterConsts.c_strSignup)) {
                if (astrParams.length < 6) {
                    Log.e(TAG, "Not enough arguments for signup");
                    throw new Exception("Not enough arguments for signup");
                }
                JSONObject jsonBody = new JSONObject();
                jsonBody.put(JSONConsts.c_strLoginField, astrParams[1]);
                jsonBody.put(JSONConsts.c_strEmailField, astrParams[2]);
                jsonBody.put(JSONConsts.c_strPasswordField, astrParams[3]);
                jsonBody.put(JSONConsts.c_strNameField, astrParams[4]);
                jsonBody.put(JSONConsts.c_strSurnameField, astrParams[5]);

                return jsonBody.toString();
            }
        } else {
            // Для слов много методов - нужно будет определять, с каким работаем
            String strMethod = astrParams[1];
            Log.e(TAG, "formJSONBody() for words not implemented yet");
            throw new Exception("formJSONBody() for words not implemented yet");
        }

        Log.e(TAG, "formJSONBody not implemented yet");
        throw new Exception("formJSONBody() not implemented yet");
    }

    // strTargetURL надо сформировать до вызова этой функции через Асеньку!
    public static String execRequest(
            String strTargetURL,
            String strBody,
            String astrMethod) {
        Log.i(TAG, astrMethod + " Request to url: " + strTargetURL + " with params " + strBody);
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(strTargetURL);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(astrMethod);
            connection.setRequestProperty("accept", "application/json");
            connection.setUseCaches(false);

            // Add request body if needed
            if (!strBody.isEmpty())
            {
                connection.setRequestProperty("Content-Type",
                        "application/json");
                connection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(strBody);
                wr.close();
                Log.d(TAG, "Sent message: " + strBody);
            }

            //Get Response
            int status = connection.getResponseCode();
            InputStream streamReceiver = null;

            // Если прилетела ошибка, то результаты не из того стрима прилетают
            if (status < 400) {
                streamReceiver = connection.getInputStream();
            } else {
                streamReceiver = connection.getErrorStream();
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(streamReceiver));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            if (status >= 400 && BuildConfig.DEBUG) {
                Log.e(TAG, "Bad response, probably error: " + response.toString());
            }
            return response.toString();
        } catch (Exception e) {
            Log.e(TAG, "Exception caught: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
