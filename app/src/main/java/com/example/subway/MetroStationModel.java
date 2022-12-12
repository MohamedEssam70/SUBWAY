package com.example.subway;

public class MetroStationModel {
    private int metroStationId;
    private int metroStationLineRelationship;
    private String metroStationName;
    private String metroStationIntersection;

    public int getMetroStationId() {
        return metroStationId;
    }

    public void setMetroStationId(int metroStationId) {
        this.metroStationId = metroStationId;
    }

    public int getMetroStationLineRelationship() {
        return metroStationLineRelationship;
    }

    public void setMetroStationLineRelationship(int metroStationLineRelationship) {
        this.metroStationLineRelationship = metroStationLineRelationship;
    }

    public String getMetroStationName() {
        return metroStationName;
    }

    public void setMetroStationName(String metroStationName) {
        this.metroStationName = metroStationName;
    }

    public String getMetroStationIntersection() {
        return metroStationIntersection;
    }

    public void setMetroStationIntersection(String metroStationIntersection) {
        this.metroStationIntersection = metroStationIntersection;
    }
}