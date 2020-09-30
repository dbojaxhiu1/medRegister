package com.example.medregister.databases;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//define table name
@Entity(tableName = "pills_table")
public class PillsData implements Serializable {
    //create an id column
    @PrimaryKey(autoGenerate = true)
    private int id;
    //create a text column
    @ColumnInfo(name = "pillName")
    private String pillName;

    @ColumnInfo(name = "pillInstruction")
    private String pillInstruction;

    @ColumnInfo(name = "numberOfPills")
    private String numberOfPills;

    @ColumnInfo(name = "dailyUsage")
    private String dailyUsage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPillName() {
        return pillName;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public String getPillInstruction() {
        return pillInstruction;
    }

    public void setPillInstruction(String pillInstruction) {
        this.pillInstruction = pillInstruction;
    }

    public String getNumberOfPills() {
        return numberOfPills;
    }

    public void setNumberOfPills(String numberOfPills) {
        this.numberOfPills = numberOfPills;
    }

    public String getDailyUsage() {
        return dailyUsage;
    }

    public void setDailyUsage(String dailyUsage) {
        this.dailyUsage = dailyUsage;
    }
}
