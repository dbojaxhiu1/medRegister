package com.example.medregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.Nullable;

public class SignedInActivity extends AppCompatActivity {

    private static final String TAG = "SignedInActivity";


    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signedin);
        Log.d(TAG, "onCreate: started.");

        setupFirebaseAuth();

        TextView registerPills = (TextView) findViewById(R.id.textView7);
        registerPills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPillsActivity();
            }
        });

        TextView schedulePills = (TextView) findViewById(R.id.textView8);
        schedulePills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedulePillsActivity();
            }
        });

        TextView notes = (TextView) findViewById(R.id.textView9);
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesActivity();
            }
        });

        TextView toDo = (TextView) findViewById(R.id.textView10);
        toDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDoActivity();
            }
        });
        TextView healthyTips = (TextView) findViewById(R.id.textView11);
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
        checkAuthenticationState();
    }

    //checks if the user is authenticated
    private void checkAuthenticationState() {
        Log.d(TAG, "checkAuthenticationState: checking authentication state.");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            Log.d(TAG, "checkAuthenticationState: user is null, navigating back to login screen.");

            Intent intent = new Intent(SignedInActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Log.d(TAG, "checkAuthenticationState: user is authenticated.");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionSignOut:
                signOut();
                return true;
            case R.id.optionAccountSettings:
                Intent intent = new Intent(SignedInActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


     //sign out the current user
    private void signOut() {
        Log.d(TAG, "signOut: signing out");
        FirebaseAuth.getInstance().signOut();
    }
    //for setting up firebase authentication
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    //user is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
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
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }
}
