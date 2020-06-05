package com.example.czechtrainer.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileObjectModel {
    private String json;
    public String email;
    public String login;
    public String name;
    public String surname;
    public String role;

    public ProfileObjectModel(String json) throws JSONException {
        this.json = json;
        assignFields();
    }

    private void assignFields() throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        this.email = jsonObject.getString("email");
        this.login = jsonObject.getString("login");
        this.name = jsonObject.getString("name");
        this.surname = jsonObject.getString("surname");
        this.role = jsonObject.getString("role");
    }
}
