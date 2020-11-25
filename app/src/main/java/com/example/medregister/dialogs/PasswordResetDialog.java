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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.Nullable;

public class PasswordResetDialog extends DialogFragment {

    private static final String TAG = "PasswordResetDialog";

    // ui widgets
    private EditText userEmail;


    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate view
        View view = inflater.inflate(R.layout.dialog_resetpassword, container, false);
        userEmail = (EditText) view.findViewById(R.id.email_password_reset);
        context = getActivity();

        // confirm dialog, will send reset password to users email, if it exists
        TextView confirmDialog = (TextView) view.findViewById(R.id.dialogConfirm);
        confirmDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmptyString(userEmail.getText().toString())) {
                    Log.d(TAG, "onClick: sending reset link to: " + userEmail.getText().toString());
                    sendPasswordResetEmail(userEmail.getText().toString());
                    getDialog().dismiss();
                }
            }
        });
        // cancel dialog, back to log in activity
        TextView cancelDialog = (TextView) view.findViewById(R.id.dialogCancel);
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }


    // Will send a password reset link to the email provided by the user
    public void sendPasswordResetEmail(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: Password Reset Email sent.");
                            Toast.makeText(context, R.string.password_reset_link_sent, Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "onComplete: No user associated with that email.");
                            Toast.makeText(context, R.string.no_user_associated_with_email, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    // return true if the string is null
    private boolean isEmptyString(String string) {
        return string.equals("");
    }
}
