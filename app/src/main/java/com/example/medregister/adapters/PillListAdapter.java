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
import com.example.medregister.databases.PillRoomDB;
import com.example.medregister.databases.PillsData;

import java.util.List;

public class PillListAdapter extends RecyclerView.Adapter<PillListAdapter.ViewHolder> {
    //initialize variables
    private List<PillsData> pillsDataList;
    private Activity context;
    private PillRoomDB database;

    public PillListAdapter(Activity context, List<PillsData> pillsDataList) {
        this.context = context;
        this.pillsDataList = pillsDataList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //initialize view

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_pill_listitem, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //initialize pill data
        PillsData pills = pillsDataList.get(position);
        //initialize database
        database = PillRoomDB.getInstance(context);
        holder.pillName.setText(pills.getPillName());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize the pill data
                PillsData p = pillsDataList.get(holder.getAdapterPosition());

                final int pillId = p.getId();
                String name = p.getPillName();
                String instruction = p.getPillInstruction();
                String nrOfPills = p.getNumberOfPills();
                String daily = p.getDailyUsage();

                final Dialog dialog = new Dialog(context);
                //set the content view
                dialog.setContentView(R.layout.dialog_pill_update);
                //initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //set layout
                dialog.getWindow().setLayout(width, height);
                dialog.show();

                final EditText pillName = dialog.findViewById(R.id.update_pill_name);
                final EditText pillInstruction = dialog.findViewById(R.id.update_instruction_pill);
                final EditText numberOfPills = dialog.findViewById(R.id.update_numOfPillsInPackage);
                final EditText dailyUsage = dialog.findViewById(R.id.update_daily_usage);
                Button buttonCancel = dialog.findViewById(R.id.update_dialogCancelPill);
                Button buttonUpdate = dialog.findViewById(R.id.dialog_update);

                pillName.setText(name);
                pillInstruction.setText(instruction);
                numberOfPills.setText(nrOfPills);
                dailyUsage.setText(daily);

                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //get update pill data from the edit text
                        dialog.dismiss();
                        String updatedName = pillName.getText().toString().trim();
                        String updatedInstruction = pillInstruction.getText().toString().trim();
                        String updatedNumberOfPills = numberOfPills.getText().toString().trim();
                        String updatedDailyUsage = dailyUsage.getText().toString().trim();
                        //update the pill name in database
                        database.mainPillsDao().update(pillId, updatedName, updatedInstruction, updatedNumberOfPills, updatedDailyUsage);
                        pillsDataList.clear();
                        pillsDataList.addAll(database.mainPillsDao().getAll());
                        notifyDataSetChanged();
                    }
                });


            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize pill data
                PillsData p = pillsDataList.get(holder.getAdapterPosition());
                //delete pill from the database
                database.mainPillsDao().delete(p);
                //notify when data is deleted
                int position = holder.getAdapterPosition();
                pillsDataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, pillsDataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return pillsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //initialize variables
        TextView pillName;
        ImageView editButton, deleteButton;
        TextView numberOfPills;
        TextView instructions;
        TextView dailyUsage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //assign those variables
            pillName = itemView.findViewById(R.id.pill_name_list);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
            numberOfPills = itemView.findViewById(R.id.pill_number_in_package_list);
            instructions = itemView.findViewById(R.id.pill_instruction_list);
            dailyUsage = itemView.findViewById(R.id.pill_daily_usage_list);
        }
    }
}
