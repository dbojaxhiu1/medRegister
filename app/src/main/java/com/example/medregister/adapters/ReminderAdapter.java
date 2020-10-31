package com.example.medregister.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medregister.R;
import com.example.medregister.models.Reminder;

public class ReminderAdapter extends ListAdapter<Reminder, ReminderAdapter.ReminderHolder> {

    private OnItemClickListener listener;

    public ReminderAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Reminder> DIFF_CALLBACK = new DiffUtil.ItemCallback<Reminder>() {
        @Override
        public boolean areItemsTheSame(@NonNull Reminder oldItem, @NonNull Reminder newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Reminder oldItem, @NonNull Reminder newItem) {
            return oldItem.getText().equals(newItem.getText()) &&
                    oldItem.getDate().equals(newItem.getDate());
        }
    };

    @NonNull
    @Override
    public ReminderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_todo, parent, false);
        return new ReminderHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderHolder holder, int position) {
        Reminder currentReminder = getItem(position);
        holder.textViewReminder.setText(currentReminder.getText());
        holder.textEditDate.setText(currentReminder.getDate());
    }


    public Reminder getReminderAt(int position) {
        return getItem(position);
    }

    class ReminderHolder extends RecyclerView.ViewHolder {
        private TextView textViewReminder;
        private TextView textEditDate;
        private RadioGroup radioGroup;
        private RadioButton radioButton;

        public ReminderHolder(@NonNull View itemView) {
            super(itemView);
            textViewReminder = itemView.findViewById(R.id.view_reminder);
            textEditDate = itemView.findViewById(R.id.date);
            //radioGroup = itemView.findViewById(R.id.radioGroup);
            //radioButton = itemView.findViewById(R.id.radio_button_reminder);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //to not click in an item with no position, avoids crash
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Reminder reminder);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}


//radioButton = (RadioButton) group.findViewById(checkedId);
//textViewReminder.setPaintFlags(textViewReminder.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);