package com.djunicode.queuingapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ruturaj on 20-01-2018.
 */

public class RecentEvents {
  @SerializedName("subject")
  private String subjectName;
  @SerializedName("batch")
  private String batchName;
  @SerializedName("from")
  private String startTime;
  @SerializedName("to")
  private String endTime;
  @SerializedName("id")
  int id;
  private String location;

  public RecentEvents(String subjectName, String batchName, String startTime, String endTime,
      String location,int id){
    this.subjectName = subjectName;
    this.batchName = batchName;
    this.startTime = startTime;
    this.endTime = endTime;
    this.location = location;
    this.id = id;
  }

  public void setSubjectName(String subjectName){
    this.subjectName = subjectName;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public void setBatchName(String batchName) {
    this.batchName = batchName;
  }

  public String getBatchName() {
    return batchName;
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
}
