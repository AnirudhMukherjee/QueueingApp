package com.djunicode.queuingapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anirudh on 03-02-2018.
 */

public class Teacher {

    @SerializedName("user")
    public int user;
    @SerializedName("name")
    public String name;
    @SerializedName("department")
    public String department;
    @SerializedName("sapID")
    public String sapID;
    @SerializedName("password")
    public String password;

    public Teacher(int user, String name, String department, String sapID, String password){
        this.user = user;
        this.department = department;
        this .name = name;
        this.sapID = sapID;
        this.password = password;
    }
}
