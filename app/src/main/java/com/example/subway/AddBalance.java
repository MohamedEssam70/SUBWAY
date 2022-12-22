package com.example.subway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.subway.Activity.MainActivity;
import com.example.subway.Activity.Registration;
import com.example.subway.Helpers.Authentication;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBalance extends AppCompatActivity {
    private boolean NoShortageData;
    private EditText addedBalance;
    private Button addBalancebtn;
    private Double extraBalance;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);
        addBalancebtn = findViewById(R.id.addBalanceBtn);
        addedBalance = findViewById(R.id.addBalanceEditTxt);
        addBalancebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoShortageData = Authentication.checkRequiredFields(AddBalance.this,
                        addedBalance.getText().toString());
                if (NoShortageData){
                    extraBalance = Double.parseDouble(addedBalance.getText().toString());
                    SharedPreferences sharedPreferences = getSharedPreferences("configurations", MODE_PRIVATE);
                    String s = sharedPreferences.getString("user", null);
                    if(s != null){
                        User user = new User();
                        user.fromJson(s);
                        user.setBalance(user.getBalance() + extraBalance);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("user", user.toJson());
                        myEdit.apply();
                        startActivity(new Intent(AddBalance.this, MainActivity.class));
                        finish();
                    }
                }
            }
        });





    }
}

