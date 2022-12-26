package com.example.subway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subway.Activity.Login;
import com.example.subway.Activity.Registration;
import com.example.subway.Activity.ResetPassword;
import com.example.subway.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassword extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private String userUID;
    DatabaseReference databaseUser;

    private EditText oldPassword;
    private EditText newPassword;
    private EditText newPasswordAgain;
    private String oldPasswordData;
    private String newPasswordData;
    private String newPasswordAgainData;
    private String userNationalId;
    private Button changePasswordBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        auth = FirebaseAuth.getInstance();
        userUID = auth.getUid();
        reference = FirebaseDatabase.getInstance().getReference("user");

        changePasswordBtn = (Button) findViewById(R.id.change_password_btn);
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPassword = (EditText) findViewById(R.id.old_password);
                newPassword = (EditText) findViewById(R.id.new_password);
                newPasswordAgain = (EditText) findViewById(R.id.new_password_again);
                oldPasswordData = oldPassword.getText().toString();
                newPasswordData = newPassword.getText().toString();
                newPasswordAgainData = newPasswordAgain.getText().toString();


                SharedPreferences sharedPreferences = getSharedPreferences("configurations", Context.MODE_PRIVATE);
                String s = sharedPreferences.getString("user", null);
                if(s != null){
                    User user = new User();
                    user.fromJson(s);
                    if(!oldPasswordData.equalsIgnoreCase(user.getPasswordData())){
                        Log.e("oldPasswordData", oldPasswordData);
                        Log.e("oldPass in Pref", user.getPasswordData());
                        Toast.makeText(ChangePassword.this , "Wrong old Password !!" , Toast.LENGTH_SHORT).show();
                    }
                    else if(newPasswordData.equalsIgnoreCase(oldPasswordData)){
                        Toast.makeText(ChangePassword.this,"You Entered the same old Password !!",Toast.LENGTH_SHORT).show();
                    }
                    else if(newPasswordData.length() < 6){
                        Toast.makeText(ChangePassword.this,"Password should be at least 6 characters !!",Toast.LENGTH_SHORT).show();
                    }
                    else if(!newPasswordData.equalsIgnoreCase(newPasswordAgainData)){
                        Toast.makeText(ChangePassword.this , "Not Match !!" , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ChangePassword.this, "Success , login with new password", Toast.LENGTH_SHORT).show();

                        databaseUser = FirebaseDatabase.getInstance().getReference("user");
                        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                userNationalId = snapshot.child(userUID).child("nationalIdData").getValue(String.class);
                                Log.e("nationalId",userNationalId);
                                Intent intent = new Intent(ChangePassword.this, Login.class);
                                intent.putExtra("oldPassword", oldPasswordData);
                                intent.putExtra("nationalId",userNationalId);
                                intent.putExtra("newPassword", newPasswordData);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(ChangePassword.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }
        });




    }
}