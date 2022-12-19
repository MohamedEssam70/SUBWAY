package com.example.subway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.subway.Helpers.STATUS;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResetPassword extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private User userRecord;

    private TextView goLoginButton;
    private EditText newPassword;
    private EditText newPasswordConfirm;
    private Button changePassword;

    private String newPasswordData;
    private String newPasswordConfirmData;

    private String userNationalId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        /**
         * Select Clickable Items
         * **/
        goLoginButton = (TextView) findViewById(R.id.signUpBackToLoginButton);
        changePassword = (Button) findViewById(R.id.changePasswordButton);

        /**
         * Select Data Field
         * **/
        newPassword = (EditText) findViewById(R.id.resetPasswordNewPasswordField);
        newPasswordConfirm = (EditText) findViewById(R.id.resetPasswordConfirmPasswordField);

        /**
         * Get the user national id
         * **/
        userNationalId = getIntent().getStringExtra("userId");

        /**
         * Firebase Initialize and get a reference to our collection
         * * **/
        firebaseDatabase = FirebaseDatabase.getInstance();

        auth = FirebaseAuth.getInstance();

        /**
         * Change Password
         * **/
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPasswordData = newPassword.getText().toString();
                newPasswordConfirmData = newPasswordConfirm.getText().toString();
                if (Authentication.checkRequiredFields(ResetPassword.this, newPasswordData, newPasswordConfirmData)){
                    //check password requirements
                    if(newPasswordData.length() < 6){
                        Toast.makeText(ResetPassword.this,"Password should be at least 6 characters !!",Toast.LENGTH_SHORT).show();
                    } else if (!newPasswordData.equals(newPasswordConfirmData)){
                        Toast.makeText(ResetPassword.this,"Not Match !!",Toast.LENGTH_SHORT).show();
                    } else {

                    }
                } else {
                    Toast.makeText(ResetPassword.this, "Enter New Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * Sub-Navigation Buttons OnClick Actions
         * **/
        goLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPassword.this, Login.class));
            }
        });
    }

    private void changePassword() {



    }
}
