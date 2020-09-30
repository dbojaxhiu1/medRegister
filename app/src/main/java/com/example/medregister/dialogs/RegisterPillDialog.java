package com.example.medregister.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.medregister.R;
import com.example.medregister.adapters.PillListAdapter;
import com.example.medregister.databases.PillRoomDB;
import com.example.medregister.databases.PillsData;

import java.util.ArrayList;
import java.util.List;

public class RegisterPillDialog extends DialogFragment {

    private static final String TAG = "RegisterPillDialog";

    private EditText mNameOfPill;
    private EditText mInstructionOfPill;
    private Context mContext;
    private EditText mDailyUsage;
    private EditText mNumOfPills;
    PillRoomDB database;
    PillListAdapter adapter;

    List<PillsData> pillList = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_registerpills, container, false);
        mNameOfPill = (EditText) view.findViewById(R.id.pill_name);
        mInstructionOfPill = (EditText) view.findViewById(R.id.instruction_pill);
        mDailyUsage = (EditText) view.findViewById(R.id.daily_usage);
        mNumOfPills = (EditText) view.findViewById(R.id.numOfPillsInPackage);
        mContext = getActivity();
        Log.d(TAG,"now im in Register pill dialog!");

        pillList = database.mainPillsDao().getAll();

        TextView addPillDialog = (TextView) view.findViewById(R.id.dialogAdd);
        addPillDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String pillName = mNameOfPill.getText().toString().trim();
                String pillInstruction = mInstructionOfPill.getText().toString().trim();
                String pillNumber = mNumOfPills.getText().toString().trim();
                String dailyUsage = mDailyUsage.getText().toString().trim();

                Log.d(TAG, "onClick: attempting to add pill");
                if (!isEmpty(pillName)
                        && !isEmpty(pillInstruction)
                        && !isEmpty(pillNumber)
                        && !isEmpty(dailyUsage)) {

                    Log.d(TAG, "onClick: creating new pill");
                    //initialize pill data
                    PillsData pill = new PillsData();
                    pill.setPillName(pillName);
                    pill.setPillInstruction(pillInstruction);
                    pill.setNumberOfPills(pillNumber);
                    pill.setDailyUsage(dailyUsage);

                    database.mainPillsDao().insert(pill);

                    pillList.clear();
                    pillList.addAll(database.mainPillsDao().getAll());
                    adapter.notifyDataSetChanged();


                    //getDialog().dismiss();

                } else {
                    Toast.makeText(mContext, "all fields must be filled out", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView cancelDialogPill = (TextView) view.findViewById(R.id.dialogCancelPill);
        cancelDialogPill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    private boolean isEmpty(String string) {
        return string.equals("");
    }
}
