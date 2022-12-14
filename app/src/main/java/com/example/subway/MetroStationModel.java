package com.example.subway;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MetroStationModel {
    private int metroStationId;
    private int metroStationLineRelationship;
    private String metroStationName;
    private int metroStationIntersection;
    private int metroStationLine;

    public MetroStationModel(int id, String metroStationName, int metroStationLineRelationship, int metroStationIntersection, int metroStationLine) {
        this.metroStationId = id;
        this.metroStationLineRelationship = metroStationLineRelationship;
        this.metroStationName = metroStationName;
        this.metroStationIntersection = metroStationIntersection;
        this.metroStationLine = metroStationLine;
    }

    public MetroStationModel(String metroStationName, int metroStationLineRelationship, int metroStationIntersection, int metroStationLine) {
        this.metroStationLineRelationship = metroStationLineRelationship;
        this.metroStationName = metroStationName;
        this.metroStationIntersection = metroStationIntersection;
        this.metroStationLine = metroStationLine;
    }

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

    public int getMetroStationIntersection() {
        return metroStationIntersection;
    }

    public void setMetroStationIntersection(int metroStationIntersection) {
        this.metroStationIntersection = metroStationIntersection;
    }

    public int getMetroStationLine() {
        return metroStationLine;
    }

    public void setMetroStationLine(int metroStationLine) {
        this.metroStationLine = metroStationLine;
    }

    @NonNull
    @Override
    public String toString() {
        return "id: "+getMetroStationId()+", name: "+getMetroStationName();
    }
}