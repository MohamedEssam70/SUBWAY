package com.example.subway;

import com.example.subway.Activity.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Trip {
    private String tripId;
    private String startStation;
    private String endStation;
    private String cost;
    private String date;

    DatabaseReference databasetrips;

    public Trip() {
    }

    public Trip(String id, String startStation, String endStation, String cost, String date) {
        this.tripId = id;
        this.startStation = startStation;
        this.endStation = endStation;
        this.cost = cost;
        this.date = date;
    }

    public String getTripId() {
        return tripId;
    }

    public String getStartStation() {
        return startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public String getCost() {
        return cost;
    }

    public String getDate() {
        return date;
    }
    public void addTrip(String date, String startStation, String endStation, String cost) {
        String userUID = MainActivity.userUID;
        databasetrips = FirebaseDatabase.getInstance().getReference("trips").child(userUID);
        String TripID = databasetrips.push().getKey();
        Trip trip = new Trip(TripID, startStation, endStation, cost, date);
        databasetrips.child(TripID).setValue(trip);
    }
}