package com.example.subway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);
        new Handler().postDelayed(new Runnable() {
            @Override

            public void run() {
                if (isFirstTime()) {
                    // go to onboarding screen
                    Intent OnboardingIntent = new Intent(Splash.this, Onboarding.class);
                    startActivity(OnboardingIntent);
                } else {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                }
                finish();
            }
        }, 5000);
    }

    private boolean isFirstTime() {
        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("configurations",MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        boolean firstTime = sharedPreferences.getBoolean("first_time", true);
        myEdit.putBoolean("first_time", false);

        myEdit.apply();

        return true;
    }
}