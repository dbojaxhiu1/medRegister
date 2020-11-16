package com.example.medregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medregister.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.Nullable;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";

    //firebase auth
    private FirebaseAuth.AuthStateListener authStateListener;

    //widgets
    private EditText userEmail, userName, userPhone;
    private ImageView userProfileImage;
    private Button save;
    private ProgressBar progressBar;
    private TextView resetPasswordLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate view
        setContentView(R.layout.activity_settings);
        Log.d(TAG, "onCreate: started.");
        userEmail = (EditText) findViewById(R.id.input_email);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        resetPasswordLink = (TextView) findViewById(R.id.change_password);
        userName = (EditText) findViewById(R.id.input_name);
        userPhone = (EditText) findViewById(R.id.input_phonenumber);
        userProfileImage = (ImageView) findViewById(R.id.user_image);
        save = (Button) findViewById(R.id.save_settings);


        setupFirebaseAuth();
        setCurrentUserEmail();
        inside();
        hideSoftKeyboard();
    }

    // save data in firebase database
    private void inside() {
        getUserAccountData();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: trying to save settings.");
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                if (!userName.getText().toString().equals("")) {
                    reference.child(getString(R.string.db_users))
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(getString(R.string.field_name))
                            .setValue(userName.getText().toString());
                }
                if (!userPhone.getText().toString().equals("")) {
                    reference.child(getString(R.string.db_users))
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(getString(R.string.field_phone))
                            .setValue(userPhone.getText().toString());
                }
                Toast.makeText(SettingsActivity.this, R.string.saved, Toast.LENGTH_SHORT).show();
            }
        });
        resetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: sending password reset link");
                //Reset Password Link
                sendResetPasswordLink();
            }
        });
    }

    // Get user data, for account settings option
    private void getUserAccountData() {
        Log.d(TAG, "getUserAccountsData: getting the users account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(getString(R.string.db_users))
                .orderByChild(getString(R.string.field_phone));

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                    User user = singleSnapshot.getValue(User.class);
                    Log.d(TAG, "onDataChange: found user: " + user.toString());
                    userName.setText(user.getName());
                    userPhone.setText(user.getPhone());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        userEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    //Will send the reset link to the email
    private void sendResetPasswordLink() {
        FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: Password Reset Email sent.");
                            Toast.makeText(SettingsActivity.this, "Sent Password Reset Link to Email", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "onComplete: No user associated with that email.");

                            Toast.makeText(SettingsActivity.this, R.string.no_user_with_this_email, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    // will set the email that is currently signed in, if there is one.
    private void setCurrentUserEmail() {
        Log.d(TAG, "setCurrentUserEmail: setting current user email to EditText field");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(TAG, "setCurrentUserEmail: user is not null.");
            String user_email = user.getEmail();
            Log.d(TAG, "setCurrentUserEmail: got the user email: " + user_email);
            userEmail.setText(user_email);
        }
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Log.d(TAG, "checkAuthenticationState: user is authenticated.");
        }
    }

    //for setting up firebase authentication
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: started.");

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(SettingsActivity.this, R.string.signed_out, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
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
