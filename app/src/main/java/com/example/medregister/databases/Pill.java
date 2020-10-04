package com.example.medregister.databases;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pill_table")
public class Pill {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String instruction;

    private int usage;

    public Pill(String name, String instruction, int usage) {
        this.name = name;
        this.instruction = instruction;
        this.usage = usage;
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
}
