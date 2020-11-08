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

import com.example.medregister.adapters.NoteAdapter;
import com.example.medregister.data.NoteDatabase;
import com.example.medregister.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    private static final String TAG = "NotesActivity";
    EditText editText;
    Button buttonAdd, buttonReset;
    RecyclerView recyclerView;

    List<Note> noteList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    NoteDatabase database;
    NoteAdapter noteAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Log.d(TAG, "onCreate: started.");
        setTitle(getString(R.string.notes_title));
        editText = findViewById(R.id.edit_text_note);
        buttonAdd = findViewById(R.id.button_add_note);
        buttonReset = findViewById(R.id.button_reset_note);
        recyclerView = findViewById(R.id.note_recycler_view);

        database = NoteDatabase.getInstance(this);
        noteList = database.NoteDao().getAll();

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        noteAdapter = new NoteAdapter(NotesActivity.this, noteList);
        recyclerView.setAdapter(noteAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteText = editText.getText().toString().trim();
                if (!noteText.equals("")) {
                    //checking when its empty
                    Note notesData = new Note();
                    notesData.setText(noteText);
                    database.NoteDao().insert(notesData);

                    editText.setText("");
                    noteList.clear();
                    noteList.addAll(database.NoteDao().getAll());
                    noteAdapter.notifyDataSetChanged();

                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.NoteDao().reset(noteList);
                noteList.clear();
                noteList.addAll(database.NoteDao().getAll());
                noteAdapter.notifyDataSetChanged();
            }
        });
    }
}
