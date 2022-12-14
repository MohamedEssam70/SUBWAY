package com.example.subway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String[] stations = {"Helwan","Ain-Helwan", "Hadyek-Helwan"};
    AutoCompleteTextView stationsAutoCompleteTxt;
    ArrayAdapter<String> adapterStations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stationsAutoCompleteTxt = findViewById(R.id.fromStationAutoComplete);
        adapterStations = new ArrayAdapter<String>(this,R.layout.dropdown_stations,stations);
        stationsAutoCompleteTxt.setAdapter(adapterStations);

        stationsAutoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String station = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"station: "+ station, Toast.LENGTH_SHORT).show();
            }
        });
        stationsAutoCompleteTxt = findViewById(R.id.toStationAutoComplete);
        adapterStations = new ArrayAdapter<String>(this,R.layout.dropdown_stations,stations);
        stationsAutoCompleteTxt.setAdapter(adapterStations);

        stationsAutoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String station = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"station: "+ station, Toast.LENGTH_SHORT).show();
            }
        });
    }
}