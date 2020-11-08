package com.example.medregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditPillActivity extends AppCompatActivity {
    private static final String TAG = "AddEditPillActivity";

    public static final String extra_name = "com.example.medregister.EXTRA_NAME";
    public static final String extra_instruction = "com.example.medregister.EXTRA_INSTRUCTION";
    public static final String extra_usage = "com.example.medregister.EXTRA_USAGE";
    public static final String extra_package_contains = "com.example.medregister.EXTRA_PACKAGE_CONTAINS";
    public static final String extra_id = "com.example.medregister.EXTRA_ID";

    private EditText editTextPillName;
    private EditText editTextPillInstruction;
    private NumberPicker numberPickerUsage;
    private NumberPicker numberPickerPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pill);
        Log.d(TAG, "onCreate: started.");
        editTextPillName = findViewById(R.id.edit_pill_name);
        editTextPillInstruction = findViewById(R.id.edit_pill_instruction);
        numberPickerUsage = findViewById(R.id.number_picker_usage);
        numberPickerPackage = findViewById(R.id.number_picker_number_in_package);

        numberPickerUsage.setMinValue(1);
        numberPickerUsage.setMaxValue(5);

        numberPickerPackage.setMinValue(1);
        numberPickerPackage.setMaxValue(50);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        addEditPillActivity();
    }

    private void savePill() {
        String name = editTextPillName.getText().toString();
        String instruction = editTextPillInstruction.getText().toString();
        int usage = numberPickerUsage.getValue();
        int packageContains = numberPickerPackage.getValue();

        if (name.trim().isEmpty() || instruction.trim().isEmpty()) {
            Toast.makeText(this, R.string.insert_pill_name_instruction, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(extra_name, name);
        data.putExtra(extra_instruction, instruction);
        data.putExtra(extra_usage, usage);
        data.putExtra(extra_package_contains, packageContains);

        int id = getIntent().getIntExtra(extra_id, -1);
        if (id != -1) {
            data.putExtra(extra_id, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_pill_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_pill:
                savePill();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addEditPillActivity() {
        Intent intent = getIntent();
        if (intent.hasExtra(extra_id)) {
            //will be triggered only if its an update situation
            setTitle(getString(R.string.edit_pill));
            editTextPillName.setText(intent.getStringExtra(extra_name));
            editTextPillInstruction.setText(intent.getStringExtra(extra_instruction));
            numberPickerUsage.setValue(intent.getIntExtra(extra_usage, 1));
            numberPickerPackage.setValue(intent.getIntExtra(extra_package_contains, 1));
        } else {
            setTitle(getString(R.string.add_pill));
        }
    }
}