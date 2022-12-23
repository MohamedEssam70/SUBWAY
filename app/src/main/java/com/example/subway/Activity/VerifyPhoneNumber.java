package com.example.subway.Activity;

import static java.lang.Integer.parseInt;

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
import androidx.appcompat.app.AppCompatActivity;

import com.example.subway.Helpers.STATUS;
import com.example.subway.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumber extends AppCompatActivity {

    public static String phoneUid;
    private String userUID;

    private Button askVerify;
    private EditText codeField;
    private TextView resendButton;
    private TextView backToLoginButton;
    private TextView errorMessages;
    private TextView contextTitle;
    private TextView contextSubTitle;
    private ProgressBar progressBar;

    private String verificationCodeBySystem;

    private FirebaseAuth oAuth;
    private DatabaseReference phoneReference;

    private CountDownTimer countDownTimer;
    private Long timeOut;

    private STATUS status;
    private String phoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_phone_number);

        /**
         * Firebase Initialize
         * * **/
        oAuth = FirebaseAuth.getInstance();
        phoneReference = FirebaseDatabase.getInstance().getReference("phoneNumber");

        /**
         * Select Clickable Items
         * **/
        askVerify = (Button) findViewById(R.id.verifyCodeButton);
        resendButton = (TextView) findViewById(R.id.resendVerifyCode);
        backToLoginButton = (TextView) findViewById(R.id.backToLoginButton);

        /**
         * Select Code Field
         * **/
        codeField = (EditText) findViewById(R.id.verifyCodeField);
        codeField.setEnabled(true);

        /**
         * Select TextViews
         * **/
        contextTitle = findViewById(R.id.title);
        contextSubTitle = findViewById(R.id.subTitle);
        errorMessages = findViewById(R.id.errorMessage);

        /**
         * Select ProgressBar
         * **/
        progressBar = findViewById(R.id.verifyProgressBar);
        progressBar.setVisibility(View.GONE);

        /**
         * Get the OTP needed reason
         * **/
        status = (STATUS) getIntent().getSerializableExtra("status");

        /**
         * Context depending on status
         * **/
        contextAdaptor();

        /**
         * Get the phone number will receive the code
         * **/
        phoneNumber = getIntent().getStringExtra("phoneNumber");

        /**
         * Set Timeout required to resend code
         * **/
        timeOut = 30L;

        /**
         * Sub-Navigation Buttons OnClick Actions
         * **/
        backToLoginButton.setOnClickListener(v -> {
            startActivity(new Intent(VerifyPhoneNumber.this, Login.class));
            finish();
        });

        /**
         * Down Counter to enable resend verification request after timeout
         * **/
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


        /**
         * Send Verify Request
         * **/
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
            Log.e("-*-*--*-*-*-*", "Get phone");
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
            Toast.makeText(VerifyPhoneNumber.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                            switch (status){
                                case VERIFYNEW:
                                    //Return Ok to Registration Class in order to store the new user data
                                    phoneUid = oAuth.getUid();
                                    setResult(Activity.RESULT_OK);

                                    finish();
                                    break;
                                case RESETPASSWORD:
                                    //Navigate to Reset Password Screen
                                    Intent intent = new Intent(VerifyPhoneNumber.this, ResetPassword.class);
                                    Log.e("userphoneUid", oAuth.getUid());
                                    phoneReference.child(oAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            userUID = snapshot.getValue().toString();
                                            Log.e("userUid", userUID);
                                            intent.putExtra("userUID", userUID);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    Log.e("i got out", "alh");
                                    finish();
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            notCorrectCodeError();
                        }
                    });
        } catch (Exception e){
            Log.e("-*-*-*--*--*--", e.getMessage());
        }
    }

    private void contextAdaptor (){
        switch (status){
            case VERIFYNEW:
                contextTitle.setText(R.string.verifyTitle);
                contextSubTitle.setText(R.string.verifySubTitle);
                backToLoginButton.setVisibility(View.GONE);
                break;
            case RESETPASSWORD:
                contextTitle.setText(R.string.resetTitle);
                contextSubTitle.setText(R.string.resetSubTitle);
                backToLoginButton.setVisibility(View.VISIBLE);
                break;
            default:
                break;
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