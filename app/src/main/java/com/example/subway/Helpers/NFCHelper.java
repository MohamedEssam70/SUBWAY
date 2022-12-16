package com.example.subway.Helpers;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.util.Log;

import com.example.subway.CheckPoint;

import java.nio.charset.Charset;
import java.util.ArrayList;

import kotlin.text.Charsets;

public class NFCHelper {

    /**
     * Name: readStationGate
     * Arguments:       intent -> context.getIntent()
     * Description: Read the Data from checkpoint as JSON using NFC Technology
     * Return: Object from CheckPoint data type
     * **/
    public static CheckPoint readStationGate(Intent intent) {
        CheckPoint checkPoint = new CheckPoint();
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
                buildTagViews(msgs);
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
    private static void buildTagViews(ArrayList<NdefMessage> msgs) {
        if (msgs == null || msgs.isEmpty()) return;
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
        } catch (Exception e) {
            Log.e("UnsupportedEncoding", e.toString());
        }
    }


}
