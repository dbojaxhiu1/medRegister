package com.example.medregister.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medregister.R;
import com.example.medregister.models.SchedulePill;

import java.util.ArrayList;
import java.util.List;

public class SchedulePillAdapter extends RecyclerView.Adapter<SchedulePillAdapter.SchedulePillHolder> {
    private static final String TAG = "SchedulePillAdapter";

    private OnItemClickListener listener;
    private List<SchedulePill> scheduledPills = new ArrayList<>();

    @NonNull
    @Override
    public SchedulePillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_scheduled_pill, parent, false);
        return new SchedulePillHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SchedulePillHolder holder, int position) {
        SchedulePill currentPill = scheduledPills.get(position);

        final String dose = currentPill.getDose() + " mg";
        holder.textViewTime.setText(currentPill.getTime());
        holder.textViewName.setText(currentPill.getPillName());
        holder.textViewDose.setText(dose);
    }

    @Override
    public int getItemCount() {
        return scheduledPills.size();
    }

    public void setScheduledPills(List<SchedulePill> scheduledPills) {
        this.scheduledPills = scheduledPills;
        notifyDataSetChanged();
    }

    public SchedulePill getScheduledPillAt(int position) {
        return scheduledPills.get(position);
    }


    class SchedulePillHolder extends RecyclerView.ViewHolder {
        private TextView textViewTime;
        private TextView textViewName;
        private TextView textViewDose;

        public SchedulePillHolder(@NonNull View itemView) {
            super(itemView);
            textViewTime = itemView.findViewById(R.id.med_time);
            textViewName = itemView.findViewById(R.id.med_name);
            textViewDose = itemView.findViewById(R.id.dose_details);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //to avoid crash check for listener, and to make sure we don't click on item with invalid position. NO_POSITION = -1
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(scheduledPills.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(SchedulePill pill);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}


