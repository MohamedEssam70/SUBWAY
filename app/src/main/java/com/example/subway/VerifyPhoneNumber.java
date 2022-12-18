package com.example.subway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumber extends AppCompatActivity {
    private Button askVerify;
    private EditText codeField;
    private ProgressBar progressBar;
    private String verificationCodeBySystem;
    private final FirebaseAuth oAuth = FirebaseAuth.getInstance();;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_phone_number);

        /*
         * Select Clickable Items
         * */
        askVerify = (Button) findViewById(R.id.verifyCodeButton);

        /*
         * Select Code Field
         * */
        codeField = (EditText) findViewById(R.id.verifyCodeField);

        /*
         * Select ProgressBar
         * */
        progressBar = findViewById(R.id.verifyProgressBar);
        progressBar.setVisibility(View.GONE);

        /*
         * get the phone number will receive the code
         * */
        final String phoneNumber = "01126765802"; //getIntent().getStringExtra("phoneNumber");


        /*
         * Send Verify Request
         * */
        //if code autofill
        sendVerificationCode(phoneNumber);
        //if user enter the code manually
        askVerify.setOnClickListener(v -> {
            String code = codeField.getText().toString();
            if (code.isEmpty()){
                Toast.makeText(this, "Enter OTP Code", Toast.LENGTH_SHORT).show();
                codeField.requestFocus();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            verifyCode(code);
        });


    }

    private void sendVerificationCode(String phoneNumber){
        try {
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(oAuth)
                            .setPhoneNumber("+2" + phoneNumber)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(this)                 // Activity (for callback binding)
                            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        } catch (Exception e){
            Log.e("-*-*-*-*-*-*-*", e.getMessage());
        }
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String otp = phoneAuthCredential.getSmsCode();
            if (otp != null){
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(otp);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyPhoneNumber.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String verificationCodeByUser) {
        if(verificationCodeBySystem != null && !verificationCodeBySystem.isEmpty()) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, verificationCodeByUser);
            signInUserByCredential(credential);
        } else {
            Log.e("--", "Wait for code message");
        }
    }

    private void signInUserByCredential(PhoneAuthCredential credential) {
        try {
            //
            oAuth.signInWithCredential(credential)
                    .addOnCompleteListener(VerifyPhoneNumber.this, task -> {
                        if (task.isSuccessful()){
                            //Store the new user and login
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e){
            Log.e("-*-*-*--*--*--", e.getMessage());
        }
    }
}
