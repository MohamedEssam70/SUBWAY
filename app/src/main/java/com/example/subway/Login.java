package com.example.subway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.subway.Helpers.STATUS;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Login extends AppCompatActivity {
    private boolean NoShortageData;
    private FirebaseAuth auth ;

    private LinearLayout loginProgress;

    private EditText loginEmail;
    private EditText loginPassword;

    private TextView registerButton;
    private TextView forgetPasswordButton;
    private Button loginButton;

    private String loginIdData;
    private String loginPasswordData;

    // to be tested for preventing login from multiple devices
    /*@Override
        protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser() != null){
            Toast.makeText(Login.this, "Already logged in !", Toast.LENGTH_SHORT).show();
        }

    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        /**
         * Select LinerView of ProgressBar
         * **/
        loginProgress = (LinearLayout) findViewById(R.id.loginProgress);

        /**
         * Select Clickable Items
         * **/
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (TextView) findViewById(R.id.loginRegisterButton);
        forgetPasswordButton = (TextView) findViewById(R.id.loginForgetPasswordButton);

        /**
         * Select Data Fields
         * **/
        loginEmail = (EditText) findViewById(R.id.loginIDField) ;
        loginPassword = (EditText) findViewById(R.id.loginPasswordField) ;

        /**
         * Firebase Initialize
         * * **/
        auth = FirebaseAuth.getInstance();

        /**
         * Disable Login Background Process Action
         * **/
        onCollectData();

        /**
         * Login Process
         * **/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginIdData = loginEmail.getText().toString();
                loginPasswordData = loginPassword.getText().toString();

                NoShortageData = Authentication.checkRequiredFields(Login.this, loginIdData, loginPasswordData);
                if (NoShortageData) {
                    if (Authentication.checkNationalId(loginIdData)) {
                        Toast.makeText(Login.this, "Incorrect National ID", Toast.LENGTH_SHORT).show();
                    }
                     else {
                        loginUser(loginIdData, loginPasswordData);
                    }
                }
            }
        });

        /**
         * Sub-Navigation Buttons OnClick Actions
         * **/
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registration.class));
            }
        });
        forgetPasswordButton.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, ResetPasswordRequest.class));
        });
    }

    private void loginUser(String loginEmail, String loginPassword) {
        onProgress();
        String loginIDEmail = loginEmail + "@metro.eg";
        auth.signInWithEmailAndPassword(loginIDEmail , loginPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                    Toast.makeText(Login.this, "Successful Login", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this ,"Wrong ID or Password",Toast.LENGTH_SHORT ).show();
                onCollectData();
            }
        });
    }

    private void onProgress(){
        loginProgress.setVisibility(View.VISIBLE);
        loginEmail.setEnabled(false);
        loginPassword.setEnabled(false);
    }
    
    private void onCollectData(){
        loginProgress.setVisibility(View.GONE);
        loginEmail.setEnabled(true);
        loginPassword.setEnabled(true);
    }

}
