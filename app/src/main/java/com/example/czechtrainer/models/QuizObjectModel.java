package com.example.czechtrainer.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuizObjectModel {
    private String json;
    public String question;
    public String answer;
    public String[] options;

    public QuizObjectModel(String json) throws JSONException {
        this.json = json;
        assignFields();
    }

    private void assignFields() throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        this.answer = jsonObject.getString("answer");
        this.question = jsonObject.getString("question");

        JSONArray jsonArray = jsonObject.getJSONArray("options");
        this.options = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++)
        {
            options[i] = jsonArray.getString(i);
        }
    }

}
