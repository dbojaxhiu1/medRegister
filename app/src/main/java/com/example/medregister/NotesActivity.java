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
    Button buttonAdd;
    NoteDatabase database;

    List<Note> noteList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate view
        setContentView(R.layout.activity_notes);
        Log.d(TAG, "onCreate: started.");
        setTitle(getString(R.string.notes_title));
        editText = findViewById(R.id.edit_text_note);
        buttonAdd = findViewById(R.id.button_add_note);

        RecyclerView recyclerView = findViewById(R.id.note_recycler_view);

        database = NoteDatabase.getInstance(this);
        noteList = database.NoteDao().getAllNotes();

        final NoteAdapter notesAdapter = new NoteAdapter(NotesActivity.this, noteList);
        recyclerView.setAdapter(notesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //call recycler view
        recyclerView.setHasFixedSize(true);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote(notesAdapter);
            }
        });
    }
    //will add note to the database
    public void addNote(NoteAdapter noteAdapter) {
        String noteText = editText.getText().toString().trim();
        //checking if the string is empty
        if (!noteText.equals("")) {
            Note notesData = new Note();
            notesData.setText(noteText);
            database.NoteDao().insert(notesData);

            editText.setText("");

            noteList.clear();
            noteList.addAll(database.NoteDao().getAllNotes());
            noteAdapter.notifyDataSetChanged();
        }
    }

}

