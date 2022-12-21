package com.example.subway;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

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

    public String toJson() {
        return new Gson().toJson(this);
    }

    public void fromJson(String json) {
        Type type = new TypeToken<CheckPoint>(){}.getType();
        CheckPoint checkPoint = new Gson().fromJson(json, type);
        this.id = checkPoint.getId();
        this.isEnter = checkPoint.isEnter();
        this.active = checkPoint.isActive();
    }
}