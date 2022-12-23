package com.example.subway;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subway.Activity.Registration;
import com.example.subway.Helpers.Authentication;
import com.example.subway.Helpers.CheckPointHelper;
import com.example.subway.Helpers.DBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    String[] stations = {"New El Marg","El Marg","Ezbet El Nakhl","Ain Shams","Mattareya","Helmeyet El Zaytoun","Hadayek El Zaytoun","Saray El Qubba","Hammamat El Qubba","Kobry El Qubba","Manshyet El Sadr","El Demerdash",
            "Ghamra","Al Shohadaa","Orabi","Nasser","El Sadat","Saad Zaghloul","Sayeda Zainab","El Malek El Saleh", "Mar Girgis" ,"El Zahraa" ,"Dar El Salam","Hadayek El Maadi", "El Maadi"   ,"Skanat El Maadi","Tura El Balad"
            ,"Kozzika","Tura El Asmant","El Maasara","Hadayek Helwan","Wadi Hof","Helwan University","Ain Helwan","Helwan","Shubra El Kheima","Koleyet El Zeraa","El Mezallat","El Khalafawy","Saint Theresa","Rod El Farag","Massara"
            ,"Attaba","Nageeb","Opera","Dokki","El Behoos","Cairo University","Faysal","Giza","Om El Masryeen","Sakyet Mekky","El Moneeb","El Haykeestep","Omar Ibn El khattab","Qubaa","Hesham Barakat","El Nozha","El Shams Club","Alf Maskan"
            ,"Heliopolis","Haroun","El Ahram","Kolleyet El Banat","Stadium","Fair Zone","El Abassiya","Abdou Pasha","El Geish","Bab El Shaariya","Maspero","Safaa Hegazy","Kit-Kat","Tawfikia","Wadi El Nile","Gamet El Dowel","Boulak El Dakrour"
            ,"Sudan","Imbaba","El-Bohy ","El-Qawmia","Ring Road","Rod El Farag Corr."
};
    private boolean NoShortageData;
    private String planTripStartStation;
    private String planTripEndStation;
    private AutoCompleteTextView stationsAutoCompleteTxt;
    private ArrayAdapter<String> adapterStations;
    private TextView balanceTxt;
    private Button planTripBtn;
    private double currentBalance;
    public boolean balanceTest = true;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        /**
         * SharedPreferences Initialize
         * **/
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("configurations", MODE_PRIVATE);
        stationsAutoCompleteTxt = view.findViewById(R.id.fromStationAutoComplete);
        adapterStations = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_stations,stations);
        stationsAutoCompleteTxt.setAdapter(adapterStations);
        Log.e("test", String.valueOf(Arrays.asList(stations).indexOf("Rod El Farag Corr.")));
        /**
         * Setting balance
         * **/
        balanceTxt =  view.findViewById(R.id.userBalanceView);
        String s = sharedPreferences.getString("user", null);
        if(s != null){
            User user = new User();
            user.fromJson(s);
            currentBalance = user.getBalance();
            balanceTxt.setText(String.valueOf(currentBalance));
        }

        balanceTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddBalance.class));
            }
        });

        /**
         * Check User Balance
         * **/
        balanceTest = getActivity().getIntent().getBooleanExtra("balance_enough", true);
        if (!balanceTest){
            Toast.makeText(getContext(), "You Don't Have Enough Money", Toast.LENGTH_SHORT).show();
        }
        /**
         * Plan Trip Input
         * **/
        stationsAutoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stationsAutoCompleteTxt.setHint("");
                planTripStartStation = parent.getItemAtPosition(position).toString();
            }
        });
        stationsAutoCompleteTxt = view.findViewById(R.id.toStationAutoComplete);
        adapterStations = new ArrayAdapter<String>(getActivity(),R.layout.dropdown_stations,stations);
        stationsAutoCompleteTxt.setAdapter(adapterStations);

        stationsAutoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stationsAutoCompleteTxt.setHint("");
                planTripEndStation = parent.getItemAtPosition(position).toString();
            }
        });
        /**
         * PLAN Trip Handling
         * **/
        planTripBtn = view.findViewById(R.id.planButton);
        planTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoShortageData = Authentication.checkRequiredFields(getActivity(),
                        planTripStartStation, planTripEndStation);
                if(NoShortageData){
                    int startId = getStationIndex(planTripStartStation);
                    int endId = getStationIndex(planTripEndStation);
                    CheckPointHelper checkPointHelper = new CheckPointHelper(getContext());
                    String intersection = checkPointHelper.getInstersctionStation(getContext(), startId, endId);
                    int count = checkPointHelper.passengerActivity(getContext(), startId, endId);
                    int cost = checkPointHelper.getCost(count);
                    Log.e("intersection", intersection);
                    Log.e("count", String.valueOf(count));
                    Log.e("cost", String.valueOf(cost));
                    if(intersection.equals("no switch needed")){
                        //TODO: UI
                    }
                    else{
                        //TODO: UI
                    }
                }

            }
        });
        /**
         * Messages Handling
         * **/
        LinearLayout messageLayout = view.findViewById(R.id.messageLayout);
        TextView messagesText = view.findViewById(R.id.messageText);
        ImageView messageIcon = view.findViewById(R.id.messageIcon);

        messageLayout.setVisibility(View.GONE);

        //TODO: Check balance here
        if (currentBalance < 10){
            messageLayout.setVisibility(View.VISIBLE);
            messageLayout.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.warning_message_layout));
            messageIcon.setImageResource(R.drawable.ic_fewbalancemessage_24);
            messagesText.setText(R.string.warningMessage1);
        }
        if (currentBalance < 7){
            messageLayout.setVisibility(View.VISIBLE);
            messageLayout.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.warning_message_layout));
            messageIcon.setImageResource(R.drawable.ic_fewbalancemessage_24);
            messagesText.setText(R.string.warningMessage2);
        }
        if (currentBalance < 5){
            messageLayout.setVisibility(View.VISIBLE);
            messageLayout.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.error_message_layout));
            messageIcon.setImageResource(R.drawable.ic_nobalancemessage_24);
            messagesText.setText(R.string.noBalanceMessage);
            messagesText.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextErrorMessage));
        }

        /**
         * User Status Indicator Handling
         * **/
        TextView textTripStatus = (TextView) view.findViewById(R.id.textTripStatus);
        ImageView circleTripStatus = (ImageView) view.findViewById(R.id.circleTripStatus);

        if (sharedPreferences.getString("check_point", null) != null){
            //User have an active trip
            textTripStatus.setText(R.string.activeTrip);
            textTripStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextEnable));
            circleTripStatus.setImageResource(R.drawable.ic_baseline_circle_green_24);
        } else {
            //User don't have active trip
            textTripStatus.setText(R.string.noActiveTrip);
            textTripStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextDisable));
            circleTripStatus.setImageResource(R.drawable.ic_baseline_circle_gray_24);
        }



        // Inflate the layout for this fragment
        return view;
    }

    private int getStationIndex(String stn) {
        return (Arrays.asList(stations).indexOf(stn) + 1);
    }
}