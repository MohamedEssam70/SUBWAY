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


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private boolean NoShortageData;
    private FirebaseAuth auth ;
    DatabaseReference reference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        /*
         * Select Clickable Items
         * */
        final TextView goLoginButton = (TextView) findViewById(R.id.signUpBackToLoginButton);
        final Button registerButton = (Button) findViewById(R.id.signUpRegisterButton);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("user");
        /*
         * Select Data Fields
         * */
         final EditText firstName = (EditText) findViewById(R.id.signUpFirstNameField);
         final EditText lastName = (EditText) findViewById(R.id.signUpLastNameField);
         final EditText nationalId = (EditText) findViewById(R.id.signUpIDField);
         final EditText password = (EditText) findViewById(R.id.signUpPasswordField);
         final EditText phoneNumber = (EditText) findViewById(R.id.signUpPhoneField);
         final EditText email = (EditText) findViewById(R.id.signUpMailField);

        /*
        * Registration Process
        * */
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Data That User Enter From EditTexts
                final String firstNameData = firstName.getText().toString();
                final String lastNameData = lastName.getText().toString();
                final String nationalIdData = nationalId.getText().toString();
                final String passwordData = password.getText().toString();
                final String phoneNumberData = phoneNumber.getText().toString();
                final String emailData = email.getText().toString();
                //Check User Enter All Required Data
                NoShortageData = Authentication.checkRequiredFields(Registration.this,
                        firstNameData, lastNameData, nationalIdData, passwordData, phoneNumberData);
                if (NoShortageData){
                    /*Map<String, Object> user = new HashMap<>();
                    user.put("first_name", firstNameData);*/
                    if(nationalIdData.length() < 14){
                        Toast.makeText(Registration.this,"Incorrect National ID",Toast.LENGTH_SHORT).show();
                    }
                    else if(passwordData.length() < 6){
                        Toast.makeText(Registration.this,"Password should be at least 6 characters !!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        registerUser(nationalIdData ,passwordData , firstNameData , lastNameData , phoneNumberData , emailData );
                    }


                }
            }
        });



        /*
         * Sub-Navigation Buttons OnClick Actions
         * */
        goLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, Login.class));
            }
        });
    }

    private void registerUser(String nationalIdData, String passwordData , String firstNameData , String lastNameData , String phoneNumberData , String emailData) {
        String nationalIDEmail = nationalIdData + "@metro.eg";
        auth.createUserWithEmailAndPassword(nationalIDEmail,passwordData).addOnCompleteListener(Registration.this , new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    User userStore = new User(firstNameData , lastNameData , nationalIdData , passwordData , phoneNumberData , emailData);
                    reference.child(auth.getUid()).setValue(userStore);
                    Toast.makeText(Registration.this, "Successful Registeration", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Registration.this, MainActivity.class));
                    finish();
                }
                else
                    Toast.makeText(Registration.this , "Registeration failed!" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
