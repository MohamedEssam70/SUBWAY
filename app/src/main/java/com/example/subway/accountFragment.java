package com.example.subway;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link accountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class accountFragment extends Fragment {
    DatabaseReference databaseUser;
    String userUID = MainActivity.userUID;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String nationalId;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public accountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment accountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static accountFragment newInstance(String param1, String param2) {
        accountFragment fragment = new accountFragment();
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
        View view = inflater.inflate(R.layout.fragment_account,container,false);
        LinearLayout tripsHistory = (LinearLayout) view.findViewById(R.id.tripHistory);
        tripsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), tripHistory.class));
            }
        });
        TextView userNameTxt = (TextView) view.findViewById(R.id.userName);
        TextView emailTxt = (TextView) view.findViewById(R.id.email);
        TextView phoneNumberTxt = (TextView) view.findViewById(R.id.phoneNumber);
        TextView nationalIdTxt = (TextView) view.findViewById(R.id.nationalID);

        databaseUser = FirebaseDatabase.getInstance().getReference("user");
        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    firstName = snapshot.child(userUID).child("firstName").getValue(String.class);
                    lastName = snapshot.child(userUID).child("lastName").getValue(String.class);
                    email = snapshot.child(userUID).child("email").getValue(String.class);
                    phoneNumber = snapshot.child(userUID).child("phoneNumberData").getValue(String.class);
                    nationalId = snapshot.child(userUID).child("nationalIdData").getValue(String.class);
                    userNameTxt.setText("Hey, "+firstName+" "+lastName);
                    emailTxt.setText(email);
                    nationalIdTxt.setText(nationalId);
                    phoneNumberTxt.setText(phoneNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        // Inflate the layout for this fragment
        return view;
    }
}