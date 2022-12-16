package com.example.subway;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subway.Helpers.CheckPointHelper;
import com.example.subway.Helpers.NFCHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import kotlin.text.Charsets;

public class MainActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter = null;
    private PendingIntent pendingIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckPointHelper checkPointHelper = new CheckPointHelper (this);

        try {
            CheckPoint Enter = new CheckPoint(48, false);
            CheckPoint Exit = new CheckPoint(48, true);

            int test = checkPointHelper.passengerActivity(this, Enter, Exit);
            TextView textView = (TextView) findViewById(R.id.hello);
            textView.setText(String.valueOf(test));
        } catch (Exception e){
            Log.e("-*--*-*-*-*-*", e.getMessage());
        }


//        try {
//
//            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
//            if (nfcAdapter == null) {
//                // Stop here, we definitely need NFC
//                Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
//                finish();
//            }
//
//            //For when the activity is launched by the intent-filter for android.nfc.action.NDEF_DISCOVERE
//            CheckPointHelper.readStationGate(this.getIntent());
//            pendingIntent = PendingIntent.getActivity(
//                    this,
//                    0,
//                    new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
//                    PendingIntent.FLAG_MUTABLE
//            );
//            IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
//            tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
//        } catch (Exception e) {
//            Log.e("UnsupportedEncoding", e.toString());
//        }
    }





    /**
     * For reading the NFC when the app is already launched
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        NFCHelper.readStationGate(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        WriteModeOff();
    }

    @Override
    protected void onResume() {
        super.onResume();
        WriteModeOn();
    }

    /******************************************************************************
     * Enable Write and foreground dispatch to prevent intent-filter to launch the app again
     ****************************************************************************/
    private void WriteModeOn() {
        if(nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    /******************************************************************************
     * Disable Write and foreground dispatch to allow intent-filter to launch the app
     ****************************************************************************/
    private void WriteModeOff() {
        if(nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }




//        MetroMapModel O = new MetroMapModel(this);
//        List<MetroStationModel> filtered = O.M3().getStationsData();
//        if(filtered.size() == 0)
//            textView.setText("No Stations Found!");
//        else {
//            textView.setText("");
//            for(MetroStationModel s: filtered) {
//                try {
//                    int sort = s.getMetroStationLines().stream().filter(
//                            item -> item.line == 3).collect(Collectors.toList()).get(0).sort;
//                    textView.setText(textView.getText() + "\n" + s.getMetroStationName() + ": " + sort);
//                } catch (Exception e) {
//                    Log.e("-*-*- ", e.getMessage());
//                }
//            }
//        }
}