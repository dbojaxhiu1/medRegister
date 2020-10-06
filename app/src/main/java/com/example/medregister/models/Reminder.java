package com.example.medregister.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "reminders_table")
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String text;

    private Date date;

    @Ignore
    public Reminder() {
    }

    @Ignore
    public Reminder(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    public Reminder(int id, String text, Date date) {
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

    public Date getDate() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse("2014-09-13");
        df.applyPattern("dd/MM/yyyy");
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
