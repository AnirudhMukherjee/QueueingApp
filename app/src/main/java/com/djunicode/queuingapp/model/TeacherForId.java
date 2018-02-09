package com.djunicode.queuingapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aniru on 04-02-2018.
 */

public class TeacherForId {

    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;

    @SerializedName("id")
    public Integer id;


    public TeacherForId(String username, String password, Integer id) {
        this.id = id;
        this.username = username;
        this.password = password;

    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
