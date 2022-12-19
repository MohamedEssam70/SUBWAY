package com.example.subway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import com.example.subway.Helpers.STATUS;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ResetPasswordRequest extends AppCompatActivity {
    private EditText phoneNumber;
    private String phoneNumberData;
    private TextView backLoginButton;
    private Button sendButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_request);

        phoneNumber = (EditText) findViewById(R.id.phoneField);
        backLoginButton = (TextView) findViewById(R.id.backToLoginButton);
        sendButton = (Button) findViewById(R.id.sendButton);

        sendButton.setOnClickListener(v -> {
            phoneNumberData = phoneNumber.getText().toString();
            if (phoneNumberData.length() !=11){
                Toast.makeText(this, "Enter Valid Number", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(ResetPasswordRequest.this, VerifyPhoneNumber.class);
                intent.putExtra("phoneNumber", phoneNumberData);
                intent.putExtra("status", STATUS.RESETPASSWORD);
                startActivity(intent);
                finish();
            }
        });

        /**
         * Sub-Navigation Buttons OnClick Actions
         * **/
        backLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPasswordRequest.this, Login.class));
            }
        });
    }

}
