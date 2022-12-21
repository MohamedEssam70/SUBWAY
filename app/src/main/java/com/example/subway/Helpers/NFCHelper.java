package com.example.subway.Helpers;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subway.Activity.MainActivity;
import com.example.subway.CheckPoint;
import com.example.subway.Trip;
import com.example.subway.User;
import com.example.subway.databinding.FragmentHomeBinding;

import java.nio.charset.Charset;
import java.util.ArrayList;

import kotlin.text.Charsets;

public class NFCHelper {


    private SharedPreferences sharedPreferences;
    private CheckPoint checkPoint = new CheckPoint();

    /**
     * Name: readStationGate
     * Arguments:       intent -> context.getIntent()
     * Description: Read the Data from checkpoint as JSON using NFC Technology
     * Return: Object from CheckPoint data type
     * **/
    public CheckPoint readStationGate(Intent intent, Context context) {
        /*********************
         * Read From NFC Tag
         *********************/
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            ArrayList<NdefMessage> msgs = new ArrayList<>();
            if (rawMsgs != null) {
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs.add(i, (NdefMessage) rawMsgs[i]);
                }
                buildTagViews(context, msgs);
            }
        }
        /****************************************************************
         * Parser the data and insert in in checkPoint object to return
         ****************************************************************/
        return checkPoint;
    }


    /**
     * Name: buildTagViews
     * Arguments:       msgs -> Array list of Ndef messages
     * Description: Private method used to decode the data that reads from the tag
     * Return: Void
     * **/
    private void buildTagViews(Context context, ArrayList<NdefMessage> msgs) {
        if (msgs == null || msgs.isEmpty()) return;
        sharedPreferences = context.getSharedPreferences("configurations", MODE_PRIVATE);
        String checkPointJson = sharedPreferences.getString("check_point", null);
        String text = "";
        byte[] payload = msgs.get(0).getRecords()[0].getPayload();
        Charset textEncoding = ((payload[0] & (byte)128) == 0)
                ? Charsets.UTF_8
                : Charsets.UTF_16;
        int languageCodeLength = (payload[0] & 51);
        try {
            // Get the Text
            text = new String(
                    payload,
                    languageCodeLength + 1,
                    payload.length - languageCodeLength - 1,
                    textEncoding
            );
            Log.e("-*-*-*-*-*-*", text);
            checkPoint.fromJson(text);
            if (checkPoint.isEnter() && checkPointJson == null){
                // This Case User don't have active trip and sign in from enter gate
                // Then we need to save check point data in sharedPreference
                SharedPreferences.Editor myEditor = sharedPreferences.edit();
                myEditor.putString("trip_status", text);
                myEditor.apply();
                Toast.makeText(context, "Trip Start Successfully", Toast.LENGTH_SHORT).show();
            } else if (!checkPoint.isEnter() && checkPointJson == null){
                // This Case User don't have active trip but sign in from exit gate
                // Then we need to prevent this action
                Toast.makeText(context, "No Active Trip to Exit", Toast.LENGTH_SHORT).show();
            } else if (!checkPoint.isEnter() && checkPointJson != null){
                // This Case User have active trip and sign out from exit gate
                // Then we need to track user activity and set the sharedPreference null
                CheckPointHelper checkPointHelper = new CheckPointHelper (context);
                String s = sharedPreferences.getString("user", null);
                    User user = new User();
                    user.fromJson(s);
                try {
                    CheckPoint Enter = new CheckPoint();
                    Enter.fromJson(sharedPreferences.getString("check_point", null));
                    CheckPoint Exit = new CheckPoint();
                    Exit.fromJson(text);

                    int count = checkPointHelper.passengerActivity(context, Enter, Exit);
                    int cost = checkPointHelper.getCost(count);

                    //check user balance is enough or not
                    if (user.getBalance() < cost){
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("balance_enough", "less");
                        context.startActivity(intent);
                    } else {
                        Trip trip = new Trip();
                        trip.setStationsCount(count);
                        trip.setEnterStation(checkPointHelper.getStationName(Enter));
                        trip.setExitStation(checkPointHelper.getStationName(Exit));
                        trip.setTripCost(cost);
                        //clear sharedPreference
                        SharedPreferences.Editor myEditor = sharedPreferences.edit();
                        myEditor.putString("trip_status", null);
                        myEditor.apply();
                        Toast.makeText(context, "Trip End Successfully", Toast.LENGTH_SHORT).show();
                        //TODO: Update user balance
                    }
                } catch (Exception e){
                    Log.e("-*--*-*-*-*-*", e.getMessage());
                }
            } else if (checkPoint.isEnter() && checkPointJson != null){
                // This Case User have active trip and sign out from enter gate
                // Then we need to prevent action
                Toast.makeText(context, "End Your Trip First", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("UnsupportedEncoding", e.toString());
        }
    }


}
