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
    public static final int EDIT_PILL_REQUEST = 2;

    private static final String TAG = "RegisterPillsActivity";

    private PillViewModel pillViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpills);
        Log.d(TAG, "onCreate: started.");
        setTitle("Register Pills");

        //create a floating action button variable
        FloatingActionButton fob_add_pill = findViewById(R.id.fob_add);
        fob_add_pill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPillsActivity.this, AddEditPillActivity.class);
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
                pillAdapter.submitList(pills);
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

        pillAdapter.setOnItemClickListener(new PillAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pill pill) {
                Intent intent = new Intent(RegisterPillsActivity.this, AddEditPillActivity.class);
                intent.putExtra(AddEditPillActivity.extra_id, pill.getId());
                intent.putExtra(AddEditPillActivity.extra_name, pill.getName());
                intent.putExtra(AddEditPillActivity.extra_instruction, pill.getInstruction());
                intent.putExtra(AddEditPillActivity.extra_usage, pill.getUsage());
                intent.putExtra(AddEditPillActivity.extra_package_contains, pill.getPackageContains());
                startActivityForResult(intent, EDIT_PILL_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PILL_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditPillActivity.extra_name);
            String instruction = data.getStringExtra(AddEditPillActivity.extra_instruction);
            int usage = data.getIntExtra(AddEditPillActivity.extra_usage, 1);
            int packageContains = data.getIntExtra(AddEditPillActivity.extra_package_contains, 1);

            Pill pill = new Pill(name, instruction, usage, packageContains);
            pillViewModel.insert(pill);

            Toast.makeText(this, "Pill saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_PILL_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditPillActivity.extra_id, -1);
            if (id == -1) {
                Toast.makeText(this, "Pill couldn't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(AddEditPillActivity.extra_name);
            String instruction = data.getStringExtra(AddEditPillActivity.extra_instruction);
            int usage = data.getIntExtra(AddEditPillActivity.extra_usage, 1);
            int packageContains = data.getIntExtra(AddEditPillActivity.extra_package_contains, 1);

            Pill pill = new Pill(name, instruction, usage, packageContains);
            pill.setId(id);
            pillViewModel.update(pill);
            Toast.makeText(this, "Pill updated", Toast.LENGTH_SHORT).show();
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
