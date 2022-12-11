package com.example.subway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResetPassword extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        final TextView goLoginButton = (TextView) findViewById(R.id.signUpBackToLoginButton);


        final EditText newPassword = (EditText) findViewById(R.id.resetPasswordNewPasswordField);
        final EditText newPasswordConfirm = (EditText) findViewById(R.id.resetPasswordConfirmPasswordField);
        final Button changePassword = (Button) findViewById(R.id.changePasswordButton);


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newPasswordData = newPassword.getText().toString();
                final String newPasswordConfirmData = newPasswordConfirm.getText().toString();
            }
        });




        goLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPassword.this, Login.class));
            }
        });
    }
}
