package com.example.medregister;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.Nullable;

public class SignedInActivity extends AppCompatActivity {

    private static final String TAG = "SignedInActivity";


    //Firebase
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate view
        setContentView(R.layout.activity_signedin);
        Log.d(TAG, "onCreate: started.");

        authentication();

        ConstraintLayout registerPills = (ConstraintLayout) findViewById(R.id.registerPillButton);
        registerPills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPillsActivity();
            }
        });

        ConstraintLayout schedulePills = (ConstraintLayout) findViewById(R.id.schedulePillButton);
        schedulePills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedulePillsActivity();
            }
        });

        ConstraintLayout notes = (ConstraintLayout) findViewById(R.id.notesButton);
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesActivity();
            }
        });

        ConstraintLayout toDo = (ConstraintLayout) findViewById(R.id.toDoButton);
        toDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDoActivity();
            }
        });
        ConstraintLayout healthyTips = (ConstraintLayout) findViewById(R.id.healthyTipsButton);
        healthyTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthyTipsActivity();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkAuthState();
    }

    //checks if the user is authenticated
    private void checkAuthState() {
        Log.d(TAG, "Checking authentication state.");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Log.d(TAG, "User is null, going back to login screen.");
            Intent intent = new Intent(SignedInActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Log.d(TAG, "User is authenticated.");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionSignOut:
                signOut(this);
                return true;
            case R.id.optionAccountSettings:
                Intent intent = new Intent(SignedInActivity.this, SettingsAndUserInfo.class);
                startActivity(intent);
                return true;
            case R.id.optionAppInformation:
                Intent intent_information = new Intent(SignedInActivity.this, AppInformationActivity.class);
                startActivity(intent_information);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //sign out the current user
    private void signOut(final Activity activity) {
        Log.d(TAG, "signOut: signing out");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.dialog_sign_out);
        builder.setMessage(R.string.dialog_sign_out_message);
        builder.setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
                FirebaseAuth.getInstance().signOut();
            }
        });
        builder.setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //for setting up firebase authentication
    private void authentication() {
        Log.d(TAG, "Firebase Authentication: started.");

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user is signed in
                    Log.d(TAG, "signed_in:" + user.getUid());
                } else {
                    //user is signed out
                    Log.d(TAG, "signed_out");
                    Intent intent = new Intent(SignedInActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    // will start register Pill activity
    private void registerPillsActivity() {
        Intent intent = new Intent(SignedInActivity.this, RegisterPillsActivity.class);
        startActivity(intent);

    }

    // will start Schedule Pill activity
    private void schedulePillsActivity() {
        Intent intent = new Intent(SignedInActivity.this, SchedulePillsActivity.class);
        startActivity(intent);
    }

    // will start Notes  activity
    private void notesActivity() {
        Intent intent = new Intent(SignedInActivity.this, NotesActivity.class);
        startActivity(intent);
    }

    // will start to dos activity
    private void toDoActivity() {
        Intent intent = new Intent(SignedInActivity.this, ToDoActivity.class);
        startActivity(intent);
    }

    // will start healthy tips activity
    private void healthyTipsActivity() {
        Intent intent = new Intent(SignedInActivity.this, HealthyTipsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
        }
    }
}
