package com.example.czechtrainer.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WordsObjectModel {
    private String json;
    public Integer count;
    public WordObject[] words;

    public class WordObject {
        private String json;
        public String id;
        public String cz;
        public String form;
        public String gender;
        public String part_of_speech;
        public String rus;

        public WordObject(String id, String json) throws JSONException {
            this.id = id;
            this.json = json;
            assignFields();
        }

        private void assignFields() throws JSONException {
            JSONObject jsonObject = new JSONObject(json);
            this.cz = jsonObject.getString("cz");
            this.form = jsonObject.getString("form").replace("null", "");
            this.gender = jsonObject.getString("gender").replace("null", "");
            this.part_of_speech = jsonObject.getString("part_of_speech").replace("null", "");
            this.rus = jsonObject.getString("rus");
        }
    }

    public WordsObjectModel(String json) throws JSONException {
        this.json = json;
        assignFields();
    }

    private void assignFields() throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        this.count = jsonObject.getInt("count");
        this.words = new WordObject[this.count];
        JSONObject jsonWordObject = jsonObject.getJSONObject("words");
        JSONArray idArray = jsonWordObject.names();
        for (int i = 0; i < this.count; i++)
        {
            String id = idArray.getString(i);
            words[i] = new WordObject(id, jsonWordObject.getString(id));
        }
    }
}
