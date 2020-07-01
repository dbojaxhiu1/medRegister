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
import com.example.medregister.models.Pill;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterPillDialog extends DialogFragment {

    private static final String TAG = "RegisterPillDialog";

    private EditText mNameOfPill;
    private EditText mIntructionOfPill;
    private Context mContext;
    private EditText mDailyUsage;
    private EditText mNumOfPills;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_registerpills, container, false);
        mNameOfPill = (EditText) view.findViewById(R.id.pill_name);
        mIntructionOfPill = (EditText) view.findViewById(R.id.instruction_pill);
        mDailyUsage = (EditText) view.findViewById(R.id.daily_usage);
        mNumOfPills = (EditText) view.findViewById(R.id.numOfPillsInPackage);
        mContext = getActivity();

        getPillData();
        TextView addPillDialog = (TextView) view.findViewById(R.id.dialogAdd);
        addPillDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to add pill");
                if (!isEmpty(mNameOfPill.getText().toString())
                        && !isEmpty(mIntructionOfPill.getText().toString())
                        && !isEmpty(mDailyUsage.getText().toString())
                        && !isEmpty(mNumOfPills.getText().toString())) {

                    Log.d(TAG,"all fields filled");
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                    if(!mNameOfPill.getText().toString().equals("")){
                        reference.child(getString(R.string.db_pills))
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(getString(R.string.field_pill_name))
                                .setValue(mNameOfPill.getText().toString());
                    }
                    if(!mIntructionOfPill.getText().toString().equals("")){
                        reference.child(getString(R.string.db_pills))
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(getString(R.string.field_pill_instruction))
                                .setValue(mIntructionOfPill.getText().toString());
                    }
                    if(!mNumOfPills.getText().toString().equals("")){
                        reference.child(getString(R.string.db_pills))
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(getString(R.string.field_number_pills))
                                .setValue(mNumOfPills.getText().toString());
                    }
                    if(!mDailyUsage.getText().toString().equals("")){
                        reference.child(getString(R.string.db_pills))
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(getString(R.string.field_daily_usage))
                                .setValue(mDailyUsage.getText().toString());
                    }
                    Toast.makeText(mContext, "Added pill", Toast.LENGTH_SHORT).show();

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

    private void getPillData(){
        Log.d(TAG,"getPillData: getting the pill data");

        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference();

        Query query = refrence.child(getString(R.string.db_pills))
                .orderByChild(getString(R.string.field_pill_name));

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot singleSnapshot : snapshot.getChildren()){
                    Pill pill = singleSnapshot.getValue(Pill.class);
                    Log.d(TAG,"onDataChange: found pills: " + pill.toString());

                    mNameOfPill.setText(pill.getPill_name());
                    mIntructionOfPill.setText(pill.getPill_instruction());
                    mNumOfPills.setText(pill.getPill_number_in_package());
                    mDailyUsage.setText(pill.getPill_daily_usage());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private boolean isEmpty(String string) {
        return string.equals("");
    }
}
