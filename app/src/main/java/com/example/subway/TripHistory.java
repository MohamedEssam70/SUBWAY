package com.example.subway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.subway.Activity.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TripHistory extends AppCompatActivity {
    ListView tripsListView;
    DatabaseReference databasetrips;
    LinkedList<Trip> tripLinkedList;
    List<Trip> trips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);
        tripsListView = findViewById(R.id.tripHistoryList);
        tripLinkedList = new LinkedList<>();
        String userUID = MainActivity.userUID;
        databasetrips = FirebaseDatabase.getInstance().getReference("trips").child(userUID);
    }


    @Override
    protected void onStart() {
        super.onStart();
        databasetrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tripLinkedList.clear();
                for(DataSnapshot tripSnapshot : snapshot.getChildren()){
                    Trip trip = tripSnapshot.getValue(Trip.class);
                    tripLinkedList.addFirst(trip);
                }
                trips = new ArrayList<>(tripLinkedList);
                TripList tripListAdapter = new TripList(TripHistory.this, trips);
                tripsListView.setAdapter(tripListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TripHistory.this, "Failed to get trips", Toast.LENGTH_SHORT).show();
            }
        });
    }
}