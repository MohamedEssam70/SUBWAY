package com.example.subway;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
    private TextView planView;
    private boolean opened;
    Button planButton;

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
        //getting start and end station
        stationsAutoCompleteTxt = view.findViewById(R.id.fromStationAutoComplete);
        adapterStations = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_stations,stations);
        stationsAutoCompleteTxt.setAdapter(adapterStations);

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
        //planing trip
        planButton = view.findViewById(R.id.planButton);
        planView = view.findViewById(R.id.tripPlan);
        planView.setVisibility(View.INVISIBLE);

        ActionBar aBar = getActivity().getActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#FF00FF00"));
        if (aBar != null) {
            aBar.setBackgroundDrawable(cd);
        }

        // using setOnclickListener on button to do hide and show
        planButton.setOnClickListener(v -> {
            if (!opened) {

                // visibility of view
                planView.setVisibility(View.VISIBLE);
                TranslateAnimation animate = new TranslateAnimation(0, 0, view.getHeight(), 0);

                // duration of animation
                animate.setDuration(500);
                animate.setFillAfter(true);
                planView.startAnimation(animate);
                planButton.setText("done");
            } else {
                planView.setVisibility(View.INVISIBLE);
                TranslateAnimation animate = new TranslateAnimation(0, 0, 0, view.getHeight());
                animate.setDuration(0);
                planView.startAnimation(animate);
                planButton.setText("Plan");
            }
            opened = !opened;
        });
        // Inflate the layout for this fragment
        return view;
    }
}