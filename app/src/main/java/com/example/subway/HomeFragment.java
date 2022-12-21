package com.example.subway;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    String[] stations = {"Helwan","Ain-helwan","Hadyek-helwan"};
    AutoCompleteTextView stationsAutoCompleteTxt;
    ArrayAdapter<String> adapterStations;

    public String balanceTest = null;


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
        stationsAutoCompleteTxt = view.findViewById(R.id.fromStationAutoComplete);
        adapterStations = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_stations,stations);
        stationsAutoCompleteTxt.setAdapter(adapterStations);
        balanceTest = getActivity().getIntent().getStringExtra("balance_enough");
        if (balanceTest != null && !balanceTest.isEmpty()){
            Toast.makeText(getContext(), "You Don't Have Enough Money", Toast.LENGTH_SHORT).show();
        }
        stationsAutoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String station = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(),"station: "+ station, Toast.LENGTH_SHORT).show();
            }
        });
        stationsAutoCompleteTxt = view.findViewById(R.id.toStationAutoComplete);
        adapterStations = new ArrayAdapter<String>(getActivity(),R.layout.dropdown_stations,stations);
        stationsAutoCompleteTxt.setAdapter(adapterStations);

        stationsAutoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stationsAutoCompleteTxt.setHint("");
                String station = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(),"station: "+ station, Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout messageLayout = view.findViewById(R.id.messageLayout);
        TextView messagesText = view.findViewById(R.id.messageText);
        ImageView messageIcon = view.findViewById(R.id.messageIcon);

        messageLayout.setVisibility(View.GONE);

        //TODO: Check balance here
        //delete temp_balance
        double temp_balance = 4.0;
        if (temp_balance < 10){
            messageLayout.setVisibility(View.VISIBLE);
            messageLayout.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.warning_message_layout));
            messageIcon.setImageResource(R.drawable.ic_fewbalancemessage_24);
            messagesText.setText(R.string.warningMessage1);
        }
        if (temp_balance < 7){
            messageLayout.setVisibility(View.VISIBLE);
            messageLayout.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.warning_message_layout));
            messageIcon.setImageResource(R.drawable.ic_fewbalancemessage_24);
            messagesText.setText(R.string.warningMessage2);
        }
        if (temp_balance < 5){
            messageLayout.setVisibility(View.VISIBLE);
            messageLayout.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.error_message_layout));
            messageIcon.setImageResource(R.drawable.ic_nobalancemessage_24);
            messagesText.setText(R.string.noBalanceMessage);
            messagesText.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextErrorMessage));
        }



        // Inflate the layout for this fragment
        return view;
    }
}