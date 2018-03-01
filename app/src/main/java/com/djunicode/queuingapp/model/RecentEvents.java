package com.djunicode.queuingapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ruturaj on 20-01-2018.
 */

public class RecentEvents {
  @SerializedName("subject")
  private String subjectName;
  @SerializedName("size")
  private int size;
  @SerializedName("from")
  private String startTime;
  @SerializedName("to")
  private String endTime;
  @SerializedName("id")
  int id;
  private String location;

  private String batch;

  public RecentEvents(String subjectName, int size, String startTime, String endTime,
          int id, String location){
    this.subjectName = subjectName;
    this.size = size;
    this.startTime = startTime;
    this.endTime = endTime;
    this.location = location;
    this.id = id;
  }
  public RecentEvents(String subjectName, String batch, int size, String startTime, String endTime,
                      String location) {
    this.subjectName = subjectName;
    this.batch = batch;
    this.size = size;
    this.startTime = startTime;
    this.endTime = endTime;
    this.location = location;
  }
  public void setSubjectName(String subjectName){
    this.subjectName = subjectName;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getSize() {
    return size;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getLocation() {
    return location;
  }
    public void setId(int id) { this.id = id;
    }
  public int getId() {
    return id;
  }

  public String getBatchName() {
    return batch;
  }

  public void setBatchName(String batch) {
    this.batch = batch;
  }
}
