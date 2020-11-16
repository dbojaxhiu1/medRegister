package com.example.medregister.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.medregister.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.Nullable;


public class ResendVerificationDialog extends DialogFragment {

    private static final String TAG = "ResendVerificationDialo";

    private EditText userConfirmPassword, userConfirmEmail;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate view
        View view = inflater.inflate(R.layout.dialog_resend_verification, container, false);
        userConfirmPassword = (EditText) view.findViewById(R.id.confirm_password);
        userConfirmEmail = (EditText) view.findViewById(R.id.confirm_email);
        context = getActivity();


        TextView confirmDialog = (TextView) view.findViewById(R.id.dialogConfirm);
        confirmDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: trying to resend verification email.");
                if (!isEmptyString(userConfirmEmail.getText().toString())
                        && !isEmptyString(userConfirmPassword.getText().toString())) {

                    authenticateAndResendEmail(userConfirmEmail.getText().toString(),
                            userConfirmPassword.getText().toString());
                } else {
                    Toast.makeText(context, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Cancel button for closing the dialog
        TextView cancelDialog = (TextView) view.findViewById(R.id.dialogCancel);
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }


    // will re-authenticate so we can send a verification email again
    private void authenticateAndResendEmail(String email, String password) {
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: re-authentication successful.");
                            sendVerificationEmail();
                            FirebaseAuth.getInstance().signOut();
                            getDialog().dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, R.string.invalid_credentials, Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });
    }

    // sends an email verification link to the user

    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, R.string.sent_verification_email, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, R.string.couldnt_send_verification_email, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    // return true if the string is null
    private boolean isEmptyString(String string) {
        return string.equals("");
    }
}
