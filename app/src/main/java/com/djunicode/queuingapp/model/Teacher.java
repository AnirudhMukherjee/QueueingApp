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
    /*@SerializedName("department")
    public String department;*/
    @SerializedName("sapId")
    public String sapID;
    @SerializedName("subject")
    public String subject;
//    @SerializedName("location")
//    public int location;
    @SerializedName("password")
    public String password;

    public Teacher(int user, String name,String password, String department, String sapID, String subject){
        this.user = user;
        //this.department = department;
        this .name = name;
        this.sapID = sapID;
        this.subject = subject;
//        this.location = location;
        this.password = password;
    }
}
