package com.example.medregister;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medregister.adapters.PillListAdapter;
import com.example.medregister.databases.PillRoomDB;
import com.example.medregister.databases.PillsData;
import com.example.medregister.dialogs.RegisterPillDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RegisterPillsActivity extends AppCompatActivity {

    private static final String TAG = "RegisterPillsActivity";

    //Initialize variable
    private ListView mListView;
    private FloatingActionButton buttonAdd;
    Button buttonEdit, buttonDelete;
    RecyclerView recyclerView;

    List<PillsData> pillList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    PillListAdapter adapter;
    PillRoomDB database;


    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpills);
        Log.d(TAG, "onCreate: started.");


        //Assign variable
        mListView = (ListView) findViewById(R.id.listView);
        buttonAdd = (FloatingActionButton) findViewById(R.id.fob);
        buttonEdit = (Button) findViewById(R.id.edit_button);
        buttonDelete = (Button) findViewById(R.id.delete_button);
        recyclerView = findViewById(R.id.pill_recyclerView);

        //initialize database
        database = PillRoomDB.getInstance(this);
        //store database values in the pill list
        pillList = database.mainPillsDao().getAll();

        //initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //set the layout
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new PillListAdapter(this,pillList);
        //set the adapter
        recyclerView.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(TAG,"Made it until here!!!!!!");

                RegisterPillDialog dialog = new RegisterPillDialog();
                dialog.show(getSupportFragmentManager(), getString(R.string.dialog_register_pill));
            }
        });
    }
}
