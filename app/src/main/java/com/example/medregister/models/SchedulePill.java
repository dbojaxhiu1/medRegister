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

    private int hour;

    private int minute;

    private int usage;

    public SchedulePill(String pillName, String dateScheduled, int usage, int hour, int minute) {
        this.pillName = pillName;
        this.dateScheduled = dateScheduled;
        this.usage = usage;
        this.hour = hour;
        this.minute = minute;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public void setDateScheduled(String dateScheduled) {
        this.dateScheduled = dateScheduled;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
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

    public int getUsage() {
        return usage;
    }
}
