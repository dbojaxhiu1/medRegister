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
    private OnItemClickListener listener;

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
        String packageString = "Package Contains: " + currentPill.getPackageContains() + " pills";
        String usage = "Daily usage: " + currentPill.getUsage();
        holder.viewPillName.setText(currentPill.getName());
        holder.viewPillInstruction.setText(currentPill.getInstruction());
        holder.viewPillUsage.setText(usage);
        holder.viewPackageContains.setText(packageString);
    }

    @Override
    public int getItemCount() {
        return pills.size();
    }

    public void setPills(List<Pill> pills) {
        this.pills = pills;
        notifyDataSetChanged();
    }

    public Pill getPillAt(int position) {
        return pills.get(position);
    }

    class PillHolder extends RecyclerView.ViewHolder {
        private TextView viewPillName;
        private TextView viewPillInstruction;
        private TextView viewPillUsage;
        private TextView viewPackageContains;

        public PillHolder(@NonNull View itemView) {
            super(itemView);
            viewPillName = itemView.findViewById(R.id.view_pill_name);
            viewPillInstruction = itemView.findViewById(R.id.view_instruction);
            viewPillUsage = itemView.findViewById(R.id.view_usage);
            viewPackageContains = itemView.findViewById(R.id.view_package_contains);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //to avoid crash check for listener, and to make sure we don't click on item with invalid position. NO_POSITION = -1
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(pills.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Pill pill);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
