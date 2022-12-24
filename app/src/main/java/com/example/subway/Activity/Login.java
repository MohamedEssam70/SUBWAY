package com.example.subway.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.subway.Helpers.Authentication;
import com.example.subway.R;
import com.example.subway.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private boolean NoShortageData;

    private FirebaseAuth auth ;
    private DatabaseReference reference;

    private LinearLayout loginProgress;

    private EditText loginEmail;
    private EditText loginPassword;

    private TextView registerButton;
    private TextView forgetPasswordButton;
    private Button loginButton;

    private String loginIdData;
    private String loginPasswordData;

    private String resetNationalId;
    private String oldPassword;
    private String newPassword;

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
        Intent i = getIntent();


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
        loginEmail = (EditText) findViewById(R.id.loginIDField);
        loginPassword = (EditText) findViewById(R.id.loginPasswordField);

        /**
         * Firebase Initialize
         * * **/
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("user");

        /**
         * Disable Login Background Process Action
         * **/
        onCollectData();
        /**
         * Reset Process
         * **/
        if(i.hasExtra("nationalId")){
            resetNationalId = getIntent().getStringExtra("nationalId");
            Log.e("nationalId", resetNationalId);
            oldPassword = getIntent().getStringExtra("oldPassword");
            Log.e("nationalId", oldPassword);
            newPassword = getIntent().getStringExtra("newPassword");
            Log.e("nationalId", newPassword);
            Log.e("intentRecived","recived");
            loginUserToResetPassword(resetNationalId, oldPassword, newPassword);
        }
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

                reference.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if(user != null) {
                            SharedPreferences sharedPreferences = getSharedPreferences("configurations", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("user", user.toJson());
                            myEdit.apply();
                            Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //
                    }
                });
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
    public void loginUserToResetPassword(String loginEmail, String loginPassword, String newPassword) {
        String loginIDEmail = loginEmail + "@metro.eg";
        auth.signInWithEmailAndPassword(loginIDEmail , loginPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        AuthCredential credential = EmailAuthProvider.getCredential(loginIDEmail, loginPassword);
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                FirebaseDatabase.getInstance().getReference("user").child(MainActivity.userUID).child("passwordData").setValue(newPassword);
                                                SharedPreferences sharedPreferences = getSharedPreferences("configurations", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                                myEdit.putString("user",null);
                                                myEdit.apply();
                                                Toast.makeText(Login.this, "password updated Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(Login.this, "can't update Password", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this ,"error can't update passwoard",Toast.LENGTH_SHORT ).show();
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
