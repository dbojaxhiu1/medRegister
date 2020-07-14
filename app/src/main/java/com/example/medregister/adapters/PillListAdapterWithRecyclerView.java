package com.example.medregister.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medregister.R;
import com.example.medregister.models.Pill;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class PillListAdapterWithRecyclerView extends RecyclerView.Adapter<PillListAdapterWithRecyclerView.ViewHolder> {

    private ArrayList<Pill> mPills = new ArrayList<>();
    private OnPillListener mOnNoteListener;

    public PillListAdapterWithRecyclerView(ArrayList<Pill> mPills, OnPillListener onPillListener) {
        this.mPills = mPills;
        this.mOnNoteListener = onPillListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pill_listitem, parent, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
//            String month = mNotes.get(position).getTimestamp().substring(0, 2);
//            month = Utility.getMonthFromNumber(month);
//            String year = mNotes.get(position).getTimestamp().substring(3);
//            String timestamp = month + " " + year;
//            holder.timestamp.setText(timestamp);
//            holder.title.setText(mNotes.get(position).getTitle());

            String pillName = mPills.get(position).getPill_name();
            holder.pill_name.setText(pillName);
            String pillInstruction = mPills.get(position).getPill_instruction();
            holder.pill_instruction.setText(pillInstruction);
            String pillNumberInPackage = mPills.get(position).getPill_name();
            holder.pill_number_in_package.setText(pillNumberInPackage);
            String pillDailyUsage = mPills.get(position).getPill_daily_usage();
            holder.pill_daily_usage.setText(pillDailyUsage);

        } catch (NullPointerException e) {
            Log.e(TAG, "onBindViewHolder: Null Pointer: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mPills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView pill_name, pill_instruction, pill_number_in_package, pill_daily_usage;
        OnPillListener mOnNoteListener;

        public ViewHolder(View itemView, OnPillListener onNoteListener) {
            super(itemView);
            pill_name = itemView.findViewById(R.id.pill_name_list);
            pill_instruction = itemView.findViewById(R.id.pill_instruction_list);
            pill_number_in_package = itemView.findViewById(R.id.pill_number_in_package_list);
            pill_daily_usage = itemView.findViewById(R.id.pill_daily_usage_list);
            mOnNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            mOnNoteListener.onPillClick(getAdapterPosition());
        }
    }

    public interface OnPillListener {
        void onPillClick(int position);
    }
}