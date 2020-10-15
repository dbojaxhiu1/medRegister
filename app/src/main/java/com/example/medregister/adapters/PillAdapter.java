package com.example.medregister.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medregister.R;
import com.example.medregister.models.Pill;

public class PillAdapter extends ListAdapter<Pill, PillAdapter.PillHolder> {

    private OnItemClickListener listener;
    private static final String TAG = "PillAdapter";

    public PillAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Pill> DIFF_CALLBACK = new DiffUtil.ItemCallback<Pill>() {
        @Override
        public boolean areItemsTheSame(@NonNull Pill oldItem, @NonNull Pill newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Pill oldItem, @NonNull Pill newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getInstruction().equals(newItem.getInstruction()) &&
                    oldItem.getUsage() == newItem.getUsage() &&
                    oldItem.getPackageContains() == newItem.getPackageContains();
        }
    };

    @NonNull
    @Override
    public PillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_pills, parent, false);
        return new PillHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PillHolder holder, int position) {
        final Pill currentPill = getItem(position);
        final String usage = "Daily usage: " + currentPill.getUsage();

       // Date currentDate = new Date();
        //Date getTime = currentPill.getCreationDate();

        //long difference = currentDate.getTime() - getTime.getTime();
        //long differenceDays = difference / (1000 * 60 * 60 * 24);

        //Log.e("day", String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH)));

        //Log.e("day", String.valueOf((getTime.get(Calendar.DAY_OF_MONTH))-currentDate.get(Calendar.DAY_OF_MONTH)));

        //if (currentDate.get(Calendar.DAY_OF_MONTH)<getTime.get(Calendar.DAY_OF_MONTH))
        //{
        //    int pillBefore = currentPill.getPackageContains() - currentPill.getUsage();
        //
        //    holder.viewPackageContains.setText(currentPill);
        //}

        String packageString = "Package Contains: " + currentPill.getPackageContains() + " pills";
        holder.viewPillName.setText(currentPill.getName());
        holder.viewPillInstruction.setText(currentPill.getInstruction());
        holder.viewPillUsage.setText(usage);
        holder.viewPackageContains.setText(packageString);
    }

    public Pill getPillAt(int position) {
        return getItem(position);
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
                        listener.onItemClick(getItem(position));
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


    /*Timer timer = new Timer();
        timer.schedule(new

        TimerTask() {

        @Override
        public void run () {
            PillAdapter.this.runOnUiThread(new Runnable() {
                public void run() {
                    // update UI here
                    int packageBefore = currentPill.getPackageContains();
                    int packageAfter = packageBefore - currentPill.getUsage();
                    holder.viewPillUsage.setText(packageAfter);
                }
            });
        }
    },0,10);*/
}

