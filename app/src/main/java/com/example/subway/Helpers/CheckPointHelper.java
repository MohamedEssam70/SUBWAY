package com.example.subway.Helpers;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

import static java.util.Arrays.stream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subway.CheckPoint;
import com.example.subway.MetroStationModel;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import kotlin.text.Charsets;

public class CheckPointHelper {
    private DBHelper dbHelper;
    private SharedPreferences sharedPreferences;
    private boolean isEnter = false;

    public CheckPointHelper(Context context){
        dbHelper = new DBHelper(context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.isEnter = sharedPreferences.getBoolean("passenger_status", false);
    }


    public boolean isEnter() {
        return isEnter;
    }


    /**
     * Name: waitExitMode
     * Arguments:       context -> need to pass the context
     * Description: Store a boolean value using SharedPreferences to check if user not end his/her trip yet
     * Return: Boolean value -> True: user still in metro
     *                          False: User exit metro
     * **/
    public void changePassengerStatus(boolean status){
        sharedPreferences.edit().putBoolean("passenger_status", status);
        this.isEnter = status;
    }


    /**
     * Name: gateInteraction
     * Description: Read the Data from checkpoint as JSON using NFC Technology
     * Return:
     * **/
    public void gateInteraction(){

    }


    /**
     * Name: passengerActivity
     * Arguments:
     * Description: Keep track of the passenger and calculate number of station in the trip
     * Return:
     * **/
    public int passengerActivity(Context context, CheckPoint enterPoint, CheckPoint exitPoint){
        List<Integer> startLines = new ArrayList<>();
        List<Integer> endLines = new ArrayList<>();
        boolean lineTransition;
        int stationsCount = 0;
        int start;
        int end;
        int mid1, mid2;
        MetroStationModel startStation = dbHelper.getStation(enterPoint.getId());
        MetroStationModel endStation = dbHelper.getStation(exitPoint.getId());
        for (int i = 0; i < startStation.getMetroStationLines().size(); i++){
            startLines.add(startStation.getMetroStationLines().get(i).line);
        }
        for (int i = 0; i < endStation.getMetroStationLines().size(); i++){
            endLines.add(endStation.getMetroStationLines().get(i).line);
        }
        List<Integer> ss = startLines.stream().distinct().filter(endLines::contains).collect(Collectors.toList());
        lineTransition = ss.isEmpty();
        if (!lineTransition){
            start = startStation.getMetroStationLines().stream().filter(
                    item -> item.line == ss.get(0)).collect(Collectors.toList()).get(0).sort;
            end = endStation.getMetroStationLines().stream().filter(
                    item -> item.line == ss.get(0)).collect(Collectors.toList()).get(0).sort;
            stationsCount = Math.abs(end - start);
        } else {
            List<Integer> intersection = getNearestIntersection(context, startLines, endLines, startStation);
            MetroStationModel intersectionStation = dbHelper.getStation(intersection.get(0));
            Log.e("/**/*/**/", intersectionStation.getMetroStationName());
            start = startStation.getMetroStationLines().stream().filter(
                    item -> item.line == intersection.get(1)).collect(Collectors.toList()).get(0).sort;
            mid1 = intersectionStation.getMetroStationLines().stream().filter(
                    item -> item.line == intersection.get(1)).collect(Collectors.toList()).get(0).sort;
            mid2 = intersectionStation.getMetroStationLines().stream().filter(
                    item -> item.line == intersection.get(2)).collect(Collectors.toList()).get(0).sort;
            end = endStation.getMetroStationLines().stream().filter(
                    item -> item.line == intersection.get(2)).collect(Collectors.toList()).get(0).sort;

            Log.e("888888", start+" - "+mid1+" + "+mid2+" - "+end);

            stationsCount = Math.abs(start - mid1) + Math.abs(mid2 - end);
        }
        return stationsCount;
    }


    /**
     * Name: getNearestIntersection
     * Arguments:
     * Description:
     * Return:
     * **/
    public List<Integer> getNearestIntersection(Context context, List<Integer> startLines, List<Integer> endLines, MetroStationModel startStation){
        List <MetroStationModel> stations;
        int intersectionId = 0;
        int intersectionLine = 0;
        int exitLine = 0;
        int start;
        int end;
        int minDistance = 1000;
        if (startLines.size() > endLines.size()){
            exitLine = endLines.get(0);
            for (Integer element : startLines) {
                start = startStation.getMetroStationLines().stream().filter(
                        item -> item.line == element).collect(Collectors.toList()).get(0).sort;
                stations = dbHelper.getIntersectionStation(element, endLines.get(0));
                for (MetroStationModel s:
                     stations) {
                    end = s.getMetroStationLines().stream().filter(
                            item -> item.line == element).collect(Collectors.toList()).get(0).sort;
                    if (Math.abs(start - end) < minDistance){
                        minDistance = Math.abs(start - end);
                        intersectionId = s.getMetroStationId();
                        intersectionLine = element;
                    }
                }
            }
        } else if (endLines.size() > startLines.size()){
            intersectionLine = startLines.get(0);
            start = startStation.getMetroStationLines().stream().filter(
                    item -> item.line == startLines.get(0)).collect(Collectors.toList()).get(0).sort;
            for (Integer element : endLines) {
                stations = dbHelper.getIntersectionStation(element, startLines.get(0));
                for (MetroStationModel s:
                        stations) {
                    end = s.getMetroStationLines().stream().filter(
                            item -> item.line == element).collect(Collectors.toList()).get(0).sort;
                    if (Math.abs(start - end) < minDistance){
                        minDistance = Math.abs(start - end);
                        intersectionId = s.getMetroStationId();
                        exitLine = element;
                    }
                }
            }
        } else {
            exitLine = endLines.get(0);
            intersectionLine = startLines.get(0);
            start = startStation.getMetroStationLines().stream().filter(
                    item -> item.line == startLines.get(0)).collect(Collectors.toList()).get(0).sort;
            stations = dbHelper.getIntersectionStation(startLines.get(0), endLines.get(0));
            for (MetroStationModel s:
                    stations) {
                end = s.getMetroStationLines().stream().filter(
                        item -> item.line == endLines.get(0)).collect(Collectors.toList()).get(0).sort;
                if (Math.abs(start - end) < minDistance){
                    minDistance = Math.abs(start - end);
                    intersectionId = s.getMetroStationId();
                }
            }
        }
        List<Integer> res = new ArrayList<>();
        res.add(0, intersectionId);
        res.add(1, intersectionLine);
        res.add(2, exitLine);
        if (intersectionId == 0 && intersectionLine ==0){
           Log.e("*--*--**-*--**", "Error in get intersection");
        }
        return res;
    }



}
