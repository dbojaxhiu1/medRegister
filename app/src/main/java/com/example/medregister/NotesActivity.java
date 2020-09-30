package com.example.medregister;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medregister.adapters.NoteListAdapter;
import com.example.medregister.databases.NotesData;
import com.example.medregister.databases.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    private static final String TAG = "NotesActivity";

    EditText editText;
    Button buttonAdd, buttonReset;
    RecyclerView recyclerView;

    List<NotesData> noteList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    NoteListAdapter noteListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Log.d(TAG, "onCreate: started.");

        editText = findViewById(R.id.edit_text);
        buttonAdd = findViewById(R.id.button_add_note);
        buttonReset = findViewById(R.id.button_reset_note);
        recyclerView = findViewById(R.id.note_recycler_view);

        //initialize database
        database = RoomDB.getInstance(this);
        noteList = database.mainNotesDao().getAll();

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        noteListAdapter = new NoteListAdapter(NotesActivity.this,noteList);
        recyclerView.setAdapter(noteListAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteText = editText.getText().toString().trim();
                if(!noteText.equals("")){
                    //checking when its empty
                    NotesData notesData = new NotesData();
                    notesData.setText(noteText);
                    database.mainNotesDao().insert(notesData);

                    editText.setText("");
                    noteList.clear();
                    noteList.addAll(database.mainNotesDao().getAll());
                    noteListAdapter.notifyDataSetChanged();

                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.mainNotesDao().reset(noteList);
                noteList.clear();
                noteList.addAll(database.mainNotesDao().getAll());
                noteListAdapter.notifyDataSetChanged();
            }
        });
    }
}
