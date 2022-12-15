package com.example.subway;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MetroStationModel {
    private int metroStationId;
    private String metroStationName;
    private List<LineModel> metroStationLines;

    public MetroStationModel(int id, String metroStationName, List<LineModel> metroStationLine) {
        this.metroStationId = id;
        this.metroStationName = metroStationName;
        this.metroStationLines = metroStationLine;
    }

    public MetroStationModel(int id, String metroStationName, String metroStationLinesJson) {
        this.metroStationId = id;
        this.metroStationName = metroStationName;
        setLinesJson(metroStationLinesJson);
    }

    public MetroStationModel(String metroStationName, List<LineModel> metroStationLine) {
        this.metroStationName = metroStationName;
        this.metroStationLines = metroStationLine;
    }

    public int getMetroStationId() {
        return metroStationId;
    }

    public void setMetroStationId(int metroStationId) {
        this.metroStationId = metroStationId;
    }

    public String getMetroStationName() {
        return metroStationName;
    }

    public void setMetroStationName(String metroStationName) {
        this.metroStationName = metroStationName;
    }

    public List<LineModel> getMetroStationLines() {
        return metroStationLines;
    }

    public void setMetroStationLines(List<LineModel> metroStationLines) {
        this.metroStationLines = metroStationLines;
    }

    public String getLinesJson() {
        return new Gson().toJson(metroStationLines);
    }

    public void setLinesJson(String json) {
        Type listType = new TypeToken<ArrayList<LineModel>>(){}.getType();
        this.metroStationLines = new Gson().fromJson(json, listType);
    }

    @NonNull
    @Override
    public String toString() {
        return "id: "+getMetroStationId()+", name: "+getMetroStationName();
    }
}