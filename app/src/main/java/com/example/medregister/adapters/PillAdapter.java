package com.example.medregister.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medregister.R;
import com.example.medregister.databases.Pill;

import java.util.ArrayList;
import java.util.List;

public class PillAdapter extends RecyclerView.Adapter<PillAdapter.PillHolder> {

    private List<Pill> pills = new ArrayList<>();

    @NonNull
    @Override
    public PillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_pills, parent, false);
        return new PillHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PillHolder holder, int position) {
        Pill currentPill = pills.get(position);
        holder.viewPillName.setText(currentPill.getName());
        holder.viewPillInstruction.setText(currentPill.getInstruction());
        holder.viewPillUsage.setText(String.valueOf(currentPill.getUsage()));
    }

    @Override
    public int getItemCount() {
        return pills.size();
    }

    public void setPills(List<Pill> pills) {
        this.pills = pills;
        notifyDataSetChanged();
    }

    class PillHolder extends RecyclerView.ViewHolder {
        private TextView viewPillName;
        private TextView viewPillInstruction;
        private TextView viewPillUsage;

        public PillHolder(@NonNull View itemView) {
            super(itemView);
            viewPillName = itemView.findViewById(R.id.view_pill_name);
            viewPillInstruction = itemView.findViewById(R.id.view_instruction);
            viewPillUsage = itemView.findViewById(R.id.view_usage);
        }

    }
}
