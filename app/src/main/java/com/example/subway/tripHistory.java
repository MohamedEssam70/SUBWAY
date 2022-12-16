package com.example.subway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class tripHistory extends AppCompatActivity {
    ListView tripsListView;
    DatabaseReference databasetrips;
    List<Trip> trips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);
        tripsListView = (ListView) findViewById(R.id.tripHistoryList);
        trips = new ArrayList<>();
        String userUID = MainActivity.userUID;
        databasetrips = FirebaseDatabase.getInstance().getReference("trips").child(userUID);
        addTrip("18/12/2022","Helwan","Maadi","5");
    }

    @Override
    protected void onStart() {
        super.onStart();
        databasetrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trips.clear();
                for(DataSnapshot tripSnapshot : snapshot.getChildren()){
                    Trip trip = tripSnapshot.getValue(Trip.class);
                    trips.add(trip);
                }
                TripList tripListAdapter = new TripList(tripHistory.this, trips);
                tripsListView.setAdapter(tripListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addTrip(String date, String startStation, String endStation, String cost) {
        String id = databasetrips.push().getKey();
        Trip trip = new Trip(id, startStation, endStation, cost, date);
        databasetrips.child(id).setValue(trip);
    }
}