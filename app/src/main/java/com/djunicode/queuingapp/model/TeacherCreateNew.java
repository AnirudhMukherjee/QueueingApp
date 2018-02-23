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
        @SerializedName("queueItems")
        public String queueItems;


        public TeacherCreateNew(String subject, int size, String from, String to,String queueItems) {
            this.subject = subject;
            this.size = size;
            this.from = from;
            this.to = to;
            this.queueItems = queueItems;
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

    public String getQueueItems(){ return queueItems;
    }
}
