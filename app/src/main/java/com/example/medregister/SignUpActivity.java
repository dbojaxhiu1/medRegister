package com.example.medregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medregister.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.Nullable;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    private static final String DOMAIN_NAME = "gmail.com";

    //widgets
    private EditText userEmail, userPassword, userConfirmPassword;
    private Button userSignUp;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate view
        setContentView(R.layout.activity_register);
        userEmail = (EditText) findViewById(R.id.input_email);
        userPassword = (EditText) findViewById(R.id.input_password);
        userConfirmPassword = (EditText) findViewById(R.id.input_confirm_password);
        userSignUp = (Button) findViewById(R.id.email_register);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: attempting to register.");

                //check for null valued EditText fields
                if (!isEmptyString(userEmail.getText().toString())
                        && !isEmptyString(userPassword.getText().toString())
                        && !isEmptyString(userConfirmPassword.getText().toString())) {
                    //check if user has a valid(@gmail.com) email address
                    if (isDomainGmail(userEmail.getText().toString())) {
                        //check if passwords match
                        if (doStringsMatch(userPassword.getText().toString(), userConfirmPassword.getText().toString())) {
                            registerNewEmail(userEmail.getText().toString(), userPassword.getText().toString());
                        } else {
                            Toast.makeText(SignUpActivity.this, R.string.passwords_dont_match, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, R.string.register_with_correct_email, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
                }
            }
        });

        hideSoftKeyboard();
    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, R.string.sent_verification_email, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, R.string.couldnt_send_verification_email, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void registerNewEmail(final String email, String password) {
        showDialog();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "onComplete: onComplete" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                            sendVerificationEmail();

                            //insert some default data
                            User user = new User();
                            user.setName(email.substring(0, email.indexOf("@")));
                            user.setPhone("1");
                            user.setProfile_image("");
                            user.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            FirebaseDatabase.getInstance().getReference()
                                    .child(getString(R.string.db_users))
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            FirebaseAuth.getInstance().signOut();
                                            //send the user to the login screen
                                            toLoginScreen();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignUpActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();
                                    //redirect the user to the login screen
                                    toLoginScreen();
                                }
                            });
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, R.string.unable_to_register, Toast.LENGTH_SHORT).show();
                        }
                        hideDialog();
                    }
                });
    }


    // Returns True if the user's email contains '@gmail.com'

    private boolean isDomainGmail(String email) {
        Log.d(TAG, "isDomainGmail: verifying if email has the correct domain: " + email);
        String domainEmail = email.substring(email.indexOf("@") + 1).toLowerCase();
        Log.d(TAG, "isDomainGmail: user domain: " + domainEmail);
        return domainEmail.equals(DOMAIN_NAME);
    }


    //Redirects the user to the login screen
    private void toLoginScreen() {
        Log.d(TAG, "redirectLoginScreen: going to login screen.");
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // return true if strings match
    private boolean doStringsMatch(String s1, String s2) {
        return s1.equals(s2);
    }

    // return true if the string is null
    private boolean isEmptyString(String string) {
        return string.equals("");
    }


    private void showDialog() {
        progressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
