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

public class AddPillActivity extends AppCompatActivity {
    private static final String TAG = "AddPillActivity";

    public static final String extra_name = "com.example.medregister.EXTRA_NAME";
    public static final String extra_instruction = "com.example.medregister.EXTRA_INSTRUCTION";
    public static final String extra_usage = "com.example.medregister.EXTRA_USAGE";

    private EditText editTextPillName;
    private EditText editTextPillInstruction;
    private NumberPicker numberPickerUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pill);
        Log.d(TAG, "onCreate: started.");
        editTextPillName = findViewById(R.id.edit_pill_name);
        editTextPillInstruction = findViewById(R.id.edit_pill_instruction);
        numberPickerUsage = findViewById(R.id.number_picker_usage);

        numberPickerUsage.setMinValue(1);
        numberPickerUsage.setMaxValue(5);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Pill");
    }

    private void savePill() {
        String name = editTextPillName.getText().toString();
        String instruction = editTextPillInstruction.getText().toString();
        int usage = numberPickerUsage.getValue();

        if (name.trim().isEmpty() || instruction.trim().isEmpty()) {
            Toast.makeText(this, "Please insert the pill name and instruction", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(extra_name, name);
        data.putExtra(extra_instruction, instruction);
        data.putExtra(extra_usage, usage);
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
}