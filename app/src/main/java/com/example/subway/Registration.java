package com.example.subway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private boolean NoShortageData;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        /*
         * Select Clickable Items
         * */
        final TextView goLoginButton = (TextView) findViewById(R.id.signUpBackToLoginButton);
        final Button registerButton = (Button) findViewById(R.id.signUpRegisterButton);

        /*
         * Select Data Fields
         * */
         final EditText firstName = (EditText) findViewById(R.id.signUpFirstNameField);
         final EditText lastName = (EditText) findViewById(R.id.signUpLastNameField);
         final EditText nationalId = (EditText) findViewById(R.id.signUpIDField);
         final EditText password = (EditText) findViewById(R.id.signUpPasswordField);
         final EditText phoneNumber = (EditText) findViewById(R.id.signUpPhoneField);
         final EditText mail = (EditText) findViewById(R.id.signUpMailField);

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
                final String mailData = mail.getText().toString();

                //Check User Enter All Required Data
                NoShortageData = Authentication.checkRequiredFields(Registration.this,
                        firstNameData, lastNameData, nationalIdData, passwordData, phoneNumberData);
                if (NoShortageData){
                    Map<String, Object> user = new HashMap<>();
                    user.put("first_name", firstNameData);
                } else {
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
}
