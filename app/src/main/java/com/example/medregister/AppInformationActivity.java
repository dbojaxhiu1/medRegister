package com.example.medregister;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AppInformationActivity extends AppCompatActivity {

    private static final String TAG = "AppInformationActivity";

    //initialize variables
    TextView textViewVersionCode;
    TextView textViewVersionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_information);
        Log.d(TAG, "onCreate: started.");
        setTitle(getString(R.string.about_app_info));

        //assign variables
        textViewVersionCode = findViewById(R.id.v_code);
        textViewVersionName = findViewById(R.id.v_name);

        //will display version code to TextView
        textViewVersionCode.setText(String.valueOf(BuildConfig.VERSION_CODE));

        //will display version name to TextView
        textViewVersionName.setText(String.valueOf(BuildConfig.VERSION_NAME));
    }
}