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
import com.example.medregister.databases.NotesData;
import com.example.medregister.databases.RoomDB;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private List<NotesData> notesDataList;
    private Activity context;
    private RoomDB database;

    public NoteListAdapter(Activity context, List<NotesData> notesDataList) {
        this.context = context;
        this.notesDataList = notesDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        NotesData notes = notesDataList.get(position);
        database = RoomDB.getInstance(context);
        holder.textView.setText(notes.getText());

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesData d = notesDataList.get(holder.getAdapterPosition());
                final int noteId = d.getId();
                String noteText = d.getText();

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update_note);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //layout
                dialog.getWindow().setLayout(width, height);
                dialog.show();

                final EditText editText = dialog.findViewById(R.id.edit_text);
                Button buttonUpdate = dialog.findViewById(R.id.button_update);

                editText.setText(noteText);

                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String updateText = editText.getText().toString().trim();
                        database.mainNotesDao().update(noteId, updateText);
                        notesDataList.clear();
                        notesDataList.addAll(database.mainNotesDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesData d = notesDataList.get(holder.getAdapterPosition());
                database.mainNotesDao().delete(d);
                int position = holder.getAdapterPosition();
                notesDataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, notesDataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView buttonEdit, buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.note_view);
            buttonEdit = itemView.findViewById(R.id.button_edit);
            buttonDelete = itemView.findViewById(R.id.button_delete);
        }
    }
}

