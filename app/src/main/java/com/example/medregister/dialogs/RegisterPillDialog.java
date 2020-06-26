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

public class RegisterPillDialog extends DialogFragment {

    private static final String TAG = "RegisterPillDialog";

    private EditText nameOfPill;
    private EditText instructionOnPill;
    private Context mContext;
    private EditText dailyUsage;
    private EditText numOfPills;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_registerpills, container, false);
        nameOfPill = (EditText) view.findViewById(R.id.pill_name);
        instructionOnPill = (EditText) view.findViewById(R.id.instruction_pill);
        dailyUsage = (EditText) view.findViewById(R.id.daily_usage);
        numOfPills = (EditText) view.findViewById(R.id.numOfPillsInPackage);
        mContext = getActivity();

        TextView addPillDialog = (TextView) view.findViewById(R.id.dialogAdd);
        addPillDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to add pill");
                if (!isEmpty(nameOfPill.getText().toString())
                        && !isEmpty(instructionOnPill.getText().toString())
                        && !isEmpty(dailyUsage.getText().toString())
                        && !isEmpty(numOfPills.getText().toString())) {
                    //list the pills using recycler viewer
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
