package com.example.subway.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.subway.Helpers.Authentication;
import com.example.subway.Helpers.STATUS;
import com.example.subway.R;
import com.example.subway.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity{

    private boolean NoShortageData;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private DatabaseReference phoneReference;

    private LinearLayout registrationProgress;

    private EditText firstName;
    private EditText lastName;
    private EditText nationalId;
    private EditText password;
    private EditText phoneNumber;
    private EditText email;

    private String firstNameData;
    private String nationalIdData;
    private String passwordData;
    private String lastNameData;
    private String phoneNumberData;
    private String emailData;

    private TextView goLoginButton;
    private Button registerButton;

    private final ActivityResultLauncher<Intent> activityForResult =
        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK){
                        registerUser();
                    }
                }
        });


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        /**
         * Select LinerView of ProgressBar
         * **/
        registrationProgress = (LinearLayout) findViewById(R.id.registerProgress);

        /**
         * Select Clickable Items
         * **/
        goLoginButton = (TextView) findViewById(R.id.signUpBackToLoginButton);
        registerButton = (Button) findViewById(R.id.signUpRegisterButton);

        /**
         * Select Data Fields
         * **/
         firstName = (EditText) findViewById(R.id.signUpFirstNameField);
         lastName = (EditText) findViewById(R.id.signUpLastNameField);
         nationalId = (EditText) findViewById(R.id.signUpIDField);
         password = (EditText) findViewById(R.id.signUpPasswordField);
         phoneNumber = (EditText) findViewById(R.id.signUpPhoneField);
         email = (EditText) findViewById(R.id.signUpMailField);

         /**
          * Firebase Initialize
          * * **/
         auth = FirebaseAuth.getInstance();
         reference = FirebaseDatabase.getInstance().getReference("user");
         phoneReference = FirebaseDatabase.getInstance().getReference("phoneNumber");
        /**
         * Disable Registration Background Process Action
         * **/
        onCollectData();

        /**
        * Registration Process
        * **/
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Data That User Enter From EditTexts
                firstNameData = firstName.getText().toString();
                lastNameData = lastName.getText().toString();
                nationalIdData = nationalId.getText().toString();
                passwordData = password.getText().toString();
                phoneNumberData = phoneNumber.getText().toString().trim();
                emailData = email.getText().toString();
                //Check User Enter All Required Data
                NoShortageData = Authentication.checkRequiredFields(Registration.this,
                        firstNameData, lastNameData, nationalIdData, passwordData, phoneNumberData);
                if (NoShortageData){
                    if(Authentication.checkNationalId(nationalIdData)){
                        Toast.makeText(Registration.this,"Incorrect National ID",Toast.LENGTH_SHORT).show();
                    }
                    else if(passwordData.length() < 6){
                        Toast.makeText(Registration.this,"Password should be at least 6 characters !!",Toast.LENGTH_SHORT).show();
                    }
                    else if(phoneNumberData.length()<11){
                        Toast.makeText(Registration.this,"Enter a valid number!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = new Intent(Registration.this, VerifyPhoneNumber.class);
                        intent.putExtra("phoneNumber", phoneNumberData);
                        intent.putExtra("status", STATUS.VERIFYNEW);
                        activityForResult.launch(intent);
                    }
                }
            }
        });

        /**
         * Sub-Navigation Buttons OnClick Actions
         * **/
        goLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, Login.class));
            }
        });
    }

    private void registerUser() {
        onProgress();
        String nationalIDEmail = nationalIdData + "@metro.eg";
        auth.createUserWithEmailAndPassword(nationalIDEmail,passwordData).addOnCompleteListener(Registration.this , new OnCompleteListener<AuthResult>() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    phoneReference.child(VerifyPhoneNumber.phoneUid).setValue(auth.getUid());
                    double balance = 0.0;
                    User userStore = new User(firstNameData , lastNameData , nationalIdData , passwordData , phoneNumberData , emailData, balance);
                    reference.child(auth.getUid()).setValue(userStore).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            SharedPreferences sharedPreferences = getSharedPreferences("configurations", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("user", userStore.toJson());
                            myEdit.apply();
                            Toast.makeText(Registration.this, "Registration Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registration.this, MainActivity.class));
                            finish();
                        }
                    });
                }
                else
                    Toast.makeText(Registration.this , "This ID is Exist Already!" , Toast.LENGTH_SHORT).show();
                    onCollectData();
            }
        });
    }

    private void onProgress(){
        registrationProgress.setVisibility(View.VISIBLE);
        firstName.setEnabled(false);
        lastName.setEnabled(false);
        nationalId.setEnabled(false);
        password.setEnabled(false);
        nationalId.setEnabled(false);
        email.setEnabled(false);
    }

    private void onCollectData(){
        registrationProgress.setVisibility(View.GONE);
        firstName.setEnabled(true);
        lastName.setEnabled(true);
        nationalId.setEnabled(true);
        password.setEnabled(true);
        nationalId.setEnabled(true);
        email.setEnabled(true);
    }
}