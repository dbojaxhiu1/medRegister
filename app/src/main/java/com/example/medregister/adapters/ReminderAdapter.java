package com.example.medregister.adapters;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medregister.R;
import com.example.medregister.models.Reminder;

import java.util.ArrayList;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderHolder> {

    private List<Reminder> reminders = new ArrayList<>();

    @NonNull
    @Override
    public ReminderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_todo, parent, false);
        return new ReminderHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderHolder holder, int position) {
        Reminder currentReminder = reminders.get(position);
        holder.textViewReminder.setText(currentReminder.getText());
        //holder.editDateTime.setText(currentReminder.getDate()); --there might be a problem here
        holder.editDateTime.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public void setReminders(List<Reminder> reminders){
        this.reminders = reminders;
        notifyDataSetChanged();
    }

    class ReminderHolder extends RecyclerView.ViewHolder {
        private TextView textViewReminder;
        private EditText editDateTime;

        public ReminderHolder(@NonNull View itemView) {
            super(itemView);
            textViewReminder = itemView.findViewById(R.id.view_reminder);
            editDateTime = itemView.findViewById(R.id.date_and_time);
        }
    }
}
