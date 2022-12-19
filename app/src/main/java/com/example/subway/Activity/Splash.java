package com.example.subway.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.subway.R;

public class Splash extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);

        // Storing data into SharedPreferences
        sharedPreferences = getSharedPreferences("configurations",MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override

            public void run() {
                if (isFirstTime()) {
                    // go to onboarding screen
                    Intent OnboardingIntent = new Intent(Splash.this, Onboarding.class);
                    startActivity(OnboardingIntent);
                } else {
                    String userJson = sharedPreferences.getString("user", null);
                    if(userJson == null) {
                        startActivity(new Intent(Splash.this, Login.class));
                    } else {
                        startActivity(new Intent(Splash.this, MainActivity.class));
                    }
                }
                finish();
            }
        }, 5000);
    }

    private boolean isFirstTime() {
        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        boolean firstTime = sharedPreferences.getBoolean("first_time", true);
        myEdit.putBoolean("first_time", false);

        myEdit.apply();

        return firstTime;
    }
}