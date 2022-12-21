package com.example.subway;

public class Trip {
    private int stationsCount;
    private String enterStation;
    private String exitStation;
    private int tripCost;
    private int tripDuration;

    public Trip(){

    }

    public int getStationsCount() {
        return stationsCount;
    }

    public void setStationsCount(int stationsCount) {
        this.stationsCount = stationsCount;
    }

    public String getEnterStation() {
        return enterStation;
    }

    public void setEnterStation(String enterStation) {
        this.enterStation = enterStation;
    }

    public String getExitStation() {
        return exitStation;
    }

    public void setExitStation(String exitStation) {
        this.exitStation = exitStation;
    }

    public int getTripCost() {
        return tripCost;
    }

    public void setTripCost(int tripCost) {
        this.tripCost = tripCost;
    }

    public int getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(int tripDuration) {
        this.tripDuration = tripDuration;
    }
}
