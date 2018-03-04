package com.djunicode.queuingapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aniru on 04-02-2018.
 */

public class TeacherCreateNew {

        @SerializedName("subject")
        public String subject;
        @SerializedName("maxLength")
        public int size;
        @SerializedName("startTime")
        public String from;
        @SerializedName("endTime")
        public String to;
        @SerializedName("queueItems")
        public String queueItems;
        @SerializedName("id")
        public int id;
        @SerializedName("teacherName")
        public String teacherName;


        public TeacherCreateNew(int id, String subject, int size, String teacherName, String from, String to,String queueItems) {
            this.subject = subject;
            this.size = size;
            this.from = from;
            this.to = to;
            this.queueItems = queueItems;
            this.id = id;
            this.teacherName= teacherName;
        }

    public String getTeacherName() {
        return teacherName;
    }

    public int getId() {
        return id;
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
