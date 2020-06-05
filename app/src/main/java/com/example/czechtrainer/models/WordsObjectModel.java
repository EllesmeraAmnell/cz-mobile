package com.example.czechtrainer.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WordsObjectModel {
    public Integer count;
    public WordObject[] words;

    public class WordObject {
        public String id;
        public String cz;
        public String form;
        public String gender;
        public String part_of_speech;
        public String rus;

        public WordObject(String id, String json) throws JSONException {
            this.id = id;
            assignFields(json);
        }

        private void assignFields(String json) throws JSONException {
            JSONObject jsonObject = new JSONObject(json);
            this.cz = jsonObject.getString("cz");
            this.form = jsonObject.getString("form");
            switch (this.form) {
                case "null":
                    this.form = " ";
                    break;
                case "singular":
                    this.form = "Ед.ч. ";
                    break;
                case "plural":
                    this.form = "мн.ч. ";
                    break;
            }
            this.gender = jsonObject.getString("gender");
            switch (this.gender) {
                case "null":
                case "no":
                    this.gender = " ";
                    break;
                case "masculine_inanimate":
                    this.gender = "Муж.р. (неодуш.) ";
                    break;
                case "masculine_animate":
                    this.gender = "Муж.р. (одуш.) ";
                    break;
                case "feminine":
                    this.gender = "Жен.р. ";
                    break;
                case "neuter":
                    this.gender = "Сред.р. ";
                    break;
            }
            this.part_of_speech = jsonObject.getString("part_of_speech");
            switch (this.part_of_speech) {
                case "verb":
                    this.part_of_speech = " Гл. ";
                    break;
                case "noun":
                    this.part_of_speech = " Сущ. ";
                    break;
                case "adjective":
                    this.part_of_speech = " Прил. ";
                    break;
                case "numeral":
                    this.part_of_speech = " Числ. ";
                    break;
                case "adverb":
                    this.part_of_speech = " Нар. ";
                    break;
            }
            this.rus = jsonObject.getString("rus").replace(", ", "\n");
        }
    }

    public WordsObjectModel(String json) throws JSONException {
        assignFields(json);
    }

    private void assignFields(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        this.count = jsonObject.getInt("count");
        this.words = new WordObject[this.count];
        JSONObject jsonWordObject = jsonObject.getJSONObject("words");
        JSONArray idArray = jsonWordObject.names();
        for (int i = 0; i < this.count; i++) {
            String id = idArray.getString(i);
            words[i] = new WordObject(id, jsonWordObject.getString(id));
        }
    }
}
