package com.djunicode.queuingapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aniru on 04-02-2018.
 */

public class TeacherForId {

    @SerializedName("id")
    public Integer id;
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
    @SerializedName("email")
    public String email;




    public TeacherForId( int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;

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

    public String getEmail() {
        return email;
    }
}
