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

    private List<SchedulePill> scheduledPills = new ArrayList<>();

    @NonNull
    @Override
    public SchedulePillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_scheduled_pill, parent, false);
        return new SchedulePillHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SchedulePillHolder holder, int position) {
        SchedulePill currentPill = scheduledPills.get(position);
        holder.textViewTime.setText(currentPill.getTime());
        holder.textViewDate.setText(currentPill.getDateScheduled());
        holder.textViewName.setText(currentPill.getPillName());
        holder.textViewDose.setText(String.valueOf(currentPill.getDose()));
    }

    @Override
    public int getItemCount() {
        return scheduledPills.size();
    }

    public void setScheduledPills(List<SchedulePill> scheduledPills){
        this.scheduledPills = scheduledPills;
        notifyDataSetChanged();
    }


    class SchedulePillHolder extends RecyclerView.ViewHolder {
        private TextView textViewTime;
        private TextView textViewDate;
        private TextView textViewName;
        private TextView textViewDose;

        public SchedulePillHolder(@NonNull View itemView) {
            super(itemView);
            textViewTime = itemView.findViewById(R.id.med_time);
            textViewDate = itemView.findViewById(R.id.med_date);
            textViewName = itemView.findViewById(R.id.med_name);
            textViewDose = itemView.findViewById(R.id.dose_details);
        }
    }
}


