package com.example.subway;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private FirebaseAuth auth ;
    private boolean NoShortageData;

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

        /*
        * Sub-Navigation Buttons OnClick Actions
        * */
        auth = FirebaseAuth.getInstance();
        final TextView registerButton = (TextView) findViewById(R.id.loginRegisterButton);
        final TextView forgetPasswordButton = (TextView) findViewById(R.id.loginForgetPasswordButton);
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final EditText loginEmail = (EditText) findViewById(R.id.loginIDField) ;
        final EditText loginPassword = (EditText) findViewById(R.id.loginPasswordField) ;



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginIdData = loginEmail.getText().toString();
                final String loginPasswordData = loginPassword.getText().toString();

                NoShortageData = Authentication.checkRequiredFields(Login.this, loginIdData, loginPasswordData);
                if (NoShortageData) {
                    /*Map<String, Object> user = new HashMap<>();
                    user.put("first_name", firstNameData);*/
                    if (loginIdData.length() < 14) {
                        Toast.makeText(Login.this, "Incorrect National ID", Toast.LENGTH_SHORT).show();
                    }
                     else {
                        loginUser(loginIdData, loginPasswordData);
                    }


                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registration.class));
            }
        });
        forgetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "testt", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, ResetPassword.class));
            }
        });
    }

    private void loginUser(String loginEmail, String loginPassword) {
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
                        Toast.makeText(Login.this ,"Wrong Id or Password",Toast.LENGTH_SHORT ).show();
                    }
                });

    }

}
