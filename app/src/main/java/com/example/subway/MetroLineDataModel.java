package com.example.subway;

public class MetroLineDataModel {
    private String metroLineId;
    private int metroLineLength;
    private double metroLineAverageMovementSpeed;

    public String getMetroLineId() {
        return metroLineId;
    }

    public void setMetroLineId(String metroLineId) {
        this.metroLineId = metroLineId;
    }

    public int getMetroLineLength() {
        return metroLineLength;
    }

    public void setMetroLineLength(int metroLineLength) {
        this.metroLineLength = metroLineLength;
    }

    public double getMetroLineAverageMovementSpeed() {
        return metroLineAverageMovementSpeed;
    }

    public void setMetroLineAverageMovementSpeed(double metroLineAverageMovementSpeed) {
        this.metroLineAverageMovementSpeed = metroLineAverageMovementSpeed;
    }
}
