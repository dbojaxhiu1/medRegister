package com.example.medregister.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminders_table")
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String text;

    private String date;

    @Ignore
    public Reminder() {
    }

    @Ignore
    public Reminder(String text, String date) {
        this.text = text;
        this.date = date;
    }

    public Reminder(int id, String text, String date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
