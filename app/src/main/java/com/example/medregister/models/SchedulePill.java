package com.example.medregister.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "schedule_table")
public class SchedulePill {

    //we use this id to uniquely identify each entry
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String pillName;

    private String dateScheduled;

   private String time;

    private int dose;

    public SchedulePill(String pillName, String dateScheduled, String time,int dose) {
        this.pillName = pillName;
        this.dateScheduled = dateScheduled;
        this.dose = dose;
        this.time = time;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public void setDateScheduled(String dateScheduled) {
        this.dateScheduled = dateScheduled;
    }

    public void setDose(int dose) {
        this.dose = dose;
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

    public String getDateScheduled() {
        return dateScheduled;
    }

    public int getDose() {
        return dose;
    }
}
