package com.example.subway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private TextView resendButton;
    private TextView errorMessages;
    private ProgressBar progressBar;
    private String verificationCodeBySystem;
    private final FirebaseAuth oAuth = FirebaseAuth.getInstance();
    private CountDownTimer countDownTimer;
    private Long timeOut = 30L;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_phone_number);

        /*
         * Select Clickable Items
         * */
        askVerify = (Button) findViewById(R.id.verifyCodeButton);
        resendButton = (TextView) findViewById(R.id.resendVerifyCode);

        /*
         * Select Code Field
         * */
        codeField = (EditText) findViewById(R.id.verifyCodeField);
        codeField.setEnabled(true);

        /*
         * Select TextViews
         * */
        errorMessages = findViewById(R.id.errorMessage);

        /*
         * Select ProgressBar
         * */
        progressBar = findViewById(R.id.verifyProgressBar);
        progressBar.setVisibility(View.GONE);

        /*
         * Get the phone number will receive the code
         * */
        final String phoneNumber = getIntent().getStringExtra("phoneNumber");

        /*
         * Down Counter to enable resend verification request after timeout
         * */
         countDownTimer = new CountDownTimer(timeOut.intValue()*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                resendButton.setText("If you don't receive SMS in " + millisUntilFinished / 1000 + " click Resend OTP Code");
            }

            @Override
            public void onFinish() {
                resendButton.setText(R.string.resendOTPRequest);
                resendButton.setOnClickListener( v -> {
                    sendVerificationCode(phoneNumber);
                    errorMessages.setText("");
                    codeField.getText().clear();
                    codeField.setEnabled(true);
                    countDownTimer.start();
                });
            }
        };


        /*
         * Send Verify Request
         * */
        //if code autofill
        sendVerificationCode(phoneNumber);
        //if user enter the code manually
        askVerify.setOnClickListener(v -> {
            String code = codeField.getText().toString();
            if (code.length() != 6){
                notValidCodeError();
                return;
            }
            validateProgress();
            verifyCode(code);
        });

    }

    private void sendVerificationCode(String phoneNumber){
        try {
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(oAuth)
                            .setPhoneNumber("+2" + phoneNumber)       // Phone number to verify
                            .setTimeout(timeOut, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(this)                 // Activity (for callback binding)
                            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
            countDownTimer.start();
        } catch (Exception e){
            Log.e("-*-*-*-*-*-*-*", e.getMessage());
        }
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String otp = phoneAuthCredential.getSmsCode();
            if (otp != null){
                validateProgress();
                verifyCode(otp);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
//            Toast.makeText(VerifyPhoneNumber.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String verificationCodeByUser) {
        if(verificationCodeBySystem != null && !verificationCodeBySystem.isEmpty()) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, verificationCodeByUser);
            signInUserByCredential(credential);
        } else {
            notValidCodeError();
        }
    }

    private void signInUserByCredential(PhoneAuthCredential credential) {
        try {
            oAuth.signInWithCredential(credential)
                    .addOnCompleteListener(VerifyPhoneNumber.this, task -> {
                        if (task.isSuccessful()){
                            //Return Ok to Registration Class in order to store the new user data
                            setResult(Activity.RESULT_OK);
                            finish();
                        } else {
                            notCorrectCodeError();
                        }
                    });
        } catch (Exception e){
            Log.e("-*-*-*--*--*--", e.getMessage());
        }
    }

    private void notValidCodeError(){
        errorMessages.setText(R.string.notValidCode);
        codeField.setEnabled(true);
        codeField.requestFocus();
        progressBar.setVisibility(View.GONE);
    }
    private void notCorrectCodeError(){
        errorMessages.setText(R.string.notCorrectCode);
        codeField.setEnabled(true);
        codeField.requestFocus();
        progressBar.setVisibility(View.GONE);
    }

    private void validateProgress(){
        progressBar.setVisibility(View.VISIBLE);
        codeField.setEnabled(false);
    }
}
