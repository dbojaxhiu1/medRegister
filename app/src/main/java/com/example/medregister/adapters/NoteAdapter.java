package com.example.medregister.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medregister.R;
import com.example.medregister.data.NoteDatabase;
import com.example.medregister.models.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> notesDataList;
    private Activity context;
    private NoteDatabase database;

    public NoteAdapter(Activity context, List<Note> notesDataList) {
        this.context = context;
        this.notesDataList = notesDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Note notes = notesDataList.get(position);
        database = NoteDatabase.getInstance(context);
        holder.textView.setText(notes.getText());

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNote(holder.getAdapterPosition());

            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(holder.getAdapterPosition());
            }
        });
    }

    public void editNote(int position) {
        final Note note = notesDataList.get(position);
        final int noteId = note.getId();
        String noteText = note.getText();

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_update_note);

        int width = WindowManager.LayoutParams.MATCH_PARENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        //layout
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        final EditText editText = dialog.findViewById(R.id.update_edit_text);
        Button buttonUpdate = dialog.findViewById(R.id.button_update);

        editText.setText(noteText);
        final TextView textView = dialog.findViewById(R.id.created_on);
        String noteCreatedOn = "Created on: " + note.getCreationDate();
        textView.setText(noteCreatedOn);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String updateText = editText.getText().toString().trim();
                database.NoteDao().update(noteId, updateText);
                notesDataList.clear();
                notesDataList.addAll(database.NoteDao().getAllNotes());
                notifyDataSetChanged();
            }
        });
    }

    public void deleteNote(int position) {
        try {
            Note note = notesDataList.get(position);
            database.NoteDao().delete(note);
            notesDataList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, notesDataList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return notesDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView buttonEdit, buttonDelete;
        TextView viewCreateOn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Get references to UI widgets
            textView = itemView.findViewById(R.id.note_view);
            buttonEdit = itemView.findViewById(R.id.button_edit);
            buttonDelete = itemView.findViewById(R.id.button_delete);
            viewCreateOn = itemView.findViewById(R.id.created_on);
        }
    }
}