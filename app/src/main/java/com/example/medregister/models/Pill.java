package com.example.medregister.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "pill_table")
public class Pill {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String instruction;

    private int usage;

    private int packageContains;

    private String creationDate = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss", Locale.getDefault()).format(new Date());


    public Pill(String name, String instruction, int usage, int packageContains) {
        this.name = name;
        this.instruction = instruction;
        this.usage = usage;
        this.packageContains = packageContains;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getInstruction() {
        return instruction;
    }

    public int getUsage() {
        return usage;
    }

    public int getPackageContains() {
        return packageContains;
    }

    public void setPackageContains(int packageContains) {
        this.packageContains = packageContains;
    }
}
