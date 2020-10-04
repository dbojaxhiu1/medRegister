package com.example.medregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medregister.adapters.PillAdapter;
import com.example.medregister.adapters.PillViewModel;
import com.example.medregister.databases.Pill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RegisterPillsActivity extends AppCompatActivity {
    public static final int ADD_PILL_REQUEST = 1;

    private static final String TAG = "RegisterPillsActivity";

    private PillViewModel pillViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpills);
        Log.d(TAG, "onCreate: started.");

        //create a floating action button variable
        FloatingActionButton fob_add_pill = findViewById(R.id.fob_add);
        fob_add_pill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPillsActivity.this, AddPillActivity.class);
                startActivityForResult(intent, ADD_PILL_REQUEST);
            }
        });

        //reference Recycler View
        RecyclerView recyclerView = findViewById(R.id.recyclerView_pill);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //call recycler view
        recyclerView.setHasFixedSize(true);

        //adapter
        final PillAdapter pillAdapter = new PillAdapter();
        //pass adapter to recycler view
        recyclerView.setAdapter(pillAdapter);

        pillViewModel = ViewModelProviders.of(this).get(PillViewModel.class);
        pillViewModel.getAllPills().observe(this, new Observer<List<Pill>>() {
            @Override
            public void onChanged(List<Pill> pills) {
                pillAdapter.setPills(pills);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pillViewModel.delete(pillAdapter.getPillAt(viewHolder.getAdapterPosition()));
                Toast.makeText(RegisterPillsActivity.this, "Pill Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PILL_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddPillActivity.extra_name);
            String instruction = data.getStringExtra(AddPillActivity.extra_instruction);
            int usage = data.getIntExtra(AddPillActivity.extra_usage, 1);

            Pill pill = new Pill(name, instruction, usage);
            pillViewModel.insert(pill);

            Toast.makeText(this, "Pill saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Pill not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.register_pill_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_pills:
                pillViewModel.deleteAllPills();
                Toast.makeText(this, "All pills deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
