package com.example.medregister;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medregister.dialogs.RegisterPillDialog;

public class RegisterPillsActivity extends AppCompatActivity {

    private static final String TAG = "RegisterPillsActivity";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpills);
        Log.d(TAG, "onCreate: started.");

        ImageButton mAddPill = (ImageButton) findViewById(R.id.addPillimageButton);
        mAddPill.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RegisterPillDialog dialog = new RegisterPillDialog();
                dialog.show(getSupportFragmentManager(), "dialog_registerpills");
            }
        });
    }
}
