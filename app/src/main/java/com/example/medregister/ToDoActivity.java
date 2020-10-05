package com.example.medregister;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ToDoActivity extends AppCompatActivity {

    private static final String TAG = "ToDoActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Log.d(TAG, "onCreate: started.");
        setTitle("To Do Activities");
    }
}
