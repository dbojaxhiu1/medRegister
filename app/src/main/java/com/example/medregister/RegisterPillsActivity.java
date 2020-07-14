package com.example.medregister;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medregister.adapters.PillListAdapter;
import com.example.medregister.dialogs.RegisterPillDialog;
import com.example.medregister.models.Pill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterPillsActivity extends AppCompatActivity {

    private static final String TAG = "RegisterPillsActivity";

    private ListView mListView;
    private FloatingActionButton mFob;

    private ArrayList<Pill> mPills;
    private PillListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpills);
        Log.d(TAG, "onCreate: started.");

        mListView = (ListView) findViewById(R.id.listView);
        mFob = (FloatingActionButton) findViewById(R.id.fob);

        inClick();
    }


    public void inClick() {

        getPills();

        mFob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterPillDialog dialog = new RegisterPillDialog();
                dialog.show(getSupportFragmentManager(), getString(R.string.dialog_register_pill));
            }
        });
    }
    private void setupPillList() {
        Log.d(TAG, "setupPillList: setting up pill listview");
        mAdapter = new PillListAdapter(RegisterPillsActivity.this, R.layout.layout_pill_listitem, mPills);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick: selected pills: " + mPills.get(i).toString());

            }
        });

    }

    private void getPills() {
        Log.d(TAG, "getPills: retrieving pills from firebase database.");
        mPills = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child(getString(R.string.db_pills));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: found pill: "
                            + singleSnapshot.getValue());
                    Pill pill = new Pill();

                    pill.setPill_id(getString(R.string.field_pill_id));
                    pill.setPill_name(getString(R.string.field_pill_name));
                    pill.setPill_instruction(getString(R.string.field_pill_instruction));
                    pill.setPill_number_in_package(getString(R.string.field_number_pills));
                    pill.setPill_daily_usage(getString(R.string.field_daily_usage));

                    mPills.add(pill);
                }
                setupPillList();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//    public void showDeleteChatroomDialog(String chatroomId){
//        DeleteChatroomDialog dialog = new DeleteChatroomDialog();
//        Bundle args = new Bundle();
//        args.putString(getString(R.string.field_chatroom_id), chatroomId);
//        dialog.setArguments(args);
//        dialog.show(getSupportFragmentManager(), getString(R.string.dialog_delete_chatroom));
//    }
    }
}
