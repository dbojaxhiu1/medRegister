package com.example.medregister.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "schedule_table")
public class SchedulePill {

    //we use this id to uniquely identify each entry
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String pillName;


    private String time;

    private String dose;

    private static int requestId = (int) System.currentTimeMillis();


    public SchedulePill(String pillName, String time, String dose) {
        this.pillName = pillName;
        this.dose = dose;
        this.time = time;
    }

    public static void setRequestId(int requestId) {
        SchedulePill.requestId = requestId;
    }

    public static int getRequestId() {
        return requestId;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPillName() {
        return pillName;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
