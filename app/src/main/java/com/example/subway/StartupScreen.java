package com.example.subway;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartupScreen extends AppCompatActivity {

    private Button getStartButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_up);

        getStartButton = findViewById(R.id.startupButton);
        getStartButton.setOnClickListener(v -> {
            startActivity(new Intent(StartupScreen.this, MainActivity.class));
            finish();
        });
    }
}
