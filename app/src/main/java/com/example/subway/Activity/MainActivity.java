package com.example.subway.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subway.CheckPoint;
import com.example.subway.Helpers.CheckPointHelper;
import com.example.subway.Helpers.NFCHelper;
import com.example.subway.HomeFragment;
import com.example.subway.R;
import com.example.subway.accountFragment;
import com.example.subway.databinding.ActivityMainBinding;
import com.example.subway.mapFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NfcAdapter nfcAdapter = null;
    PendingIntent pendingIntent = null;
    NFCHelper nfcHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        Toast.makeText(this, "testing", Toast.LENGTH_SHORT).show();
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.map:
                    replaceFragment(new mapFragment());
                    break;
                case R.id.account:
                    replaceFragment(new accountFragment());
                    break;
            }
            return true;
        });


        /*********************************
         * NFC HANDLING
         *********************************/
        try {
            NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            /**
             * Check if the device Support NFC or not
             * **/
            if (nfcAdapter == null) {
                // Stop here, we definitely need NFC
                Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
                finish();
            }

            /**
             *
             * **/
            //For when the activity is launched by the intent-filter for android.nfc.action.NDEF_DISCOVERE
            nfcHelper.readStationGate(this.getIntent(), this);
            pendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                    PendingIntent.FLAG_MUTABLE
            );
            IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
            tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        } catch (Exception e) {
            Log.e("UnsupportedEncoding", e.toString());
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();
    }



    /******************************************************************************
     * For reading the NFC when the app is already launched
     ****************************************************************************/
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        nfcHelper.readStationGate(intent, this);
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

    /*
    * TODO: Write on NFC tag
    * */
    /**NOT COMPLETE TASK**/
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
}