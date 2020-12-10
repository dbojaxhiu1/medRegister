package com.example.medregister.data.dao;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.example.medregister.data.NoteDatabase;
import com.example.medregister.models.Note;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class NotesDaoTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private NoteDatabase database;
    private NotesDao notesDao;

    @Before
    public void setUp() throws Exception {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                NoteDatabase.class
        ).allowMainThreadQueries().build();

        notesDao = database.NoteDao();
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void insert() {
        Note note = new Note();
        note.setText("text");
        note.setCreationDate("12:12");
        database.NoteDao().insert(note);

        List<Note> insertedReminders = notesDao.getAllNotes();

        assertNotNull(insertedReminders);
        assertEquals(note.getText(), insertedReminders.get(0).getText());
        assertEquals(note.getCreationDate(), insertedReminders.get(0).getCreationDate());
    }

    @Test
    public void delete() {
        Note note = new Note();
        note.setText("text");
        note.setCreationDate("12:12");
        database.NoteDao().insert(note);

        List<Note> insertedReminders = notesDao.getAllNotes();

        assertNotNull(insertedReminders);
        database.NoteDao().delete(note);
    }

    @Test
    public void update() {
        Note note = new Note();
        note.setText("text");
        note.setCreationDate("12:12");
        note.setId(1);
        database.NoteDao().insert(note);

        List<Note> insertedReminders = notesDao.getAllNotes();

        assertNotNull(insertedReminders);
        database.NoteDao().update(note.getId(), note.getText());
    }
}