package com.djunicode.queuingapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aniru on 04-02-2018.
 */

public class TeacherCreateNew {

        @SerializedName("subject")
        public String subject;
        @SerializedName("batch")
        public String batch;
        @SerializedName("from")
        public String from;
        @SerializedName("to")
        public String to;


        public TeacherCreateNew(String subject, String batch, String from, String to) {
            this.subject = subject;
            this.batch = batch;
            this.from = from;
            this.to = to;
        }

    public String getSubject() {
        return subject;
    }

    public String getBatch() {
        return batch;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
