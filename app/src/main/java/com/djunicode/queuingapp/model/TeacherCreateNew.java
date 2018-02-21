package com.djunicode.queuingapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aniru on 04-02-2018.
 */

public class TeacherCreateNew {

        @SerializedName("subject")
        public String subject;
        @SerializedName("size")
        public int size;
        @SerializedName("startTime")
        public String from;
        @SerializedName("endTime")
        public String to;


        public TeacherCreateNew(String subject, int size, String from, String to) {
            this.subject = subject;
            this.size = size;
            this.from = from;
            this.to = to;
        }

    public String getSubject() {
        return subject;
    }

    public int getSize() {
        return size;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
