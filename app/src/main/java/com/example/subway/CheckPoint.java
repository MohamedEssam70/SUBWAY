package com.example.subway;

public class CheckPoint {
    private int id;
    private boolean isEnter;
    private boolean active;

    public CheckPoint(){
        this.active = false;
    }

    public CheckPoint(int id, boolean isEnter){
        this.id = id;
        this.isEnter = isEnter;
        this.active = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnter() {
        return isEnter;
    }

    public void setEnter(boolean enter) {
        isEnter = enter;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}