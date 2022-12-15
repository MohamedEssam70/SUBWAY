package com.example.subway;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.hello);
        MetroMapModel O = new MetroMapModel(this);
//        List<MetroStationModel> filtered = O.M1().getStationsData().stream().filter(
//            item -> item.getMetroStationId() == 00).collect(Collectors.toList());
        List<MetroStationModel> filtered = O.M1().getStationsData();
        if(filtered.size() == 0)
            textView.setText("No Stations Found!");
        else {
            textView.setText("");
            for(MetroStationModel s: filtered) {
                textView.setText(textView.getText() + "\n" + s.getMetroStationName() + ": " + s.getMetroStationLines().get(0).sort);
            }
        }
    }
}