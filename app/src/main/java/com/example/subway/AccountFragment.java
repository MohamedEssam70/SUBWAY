package com.example.subway;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.subway.Activity.Login;
import com.example.subway.Activity.MainActivity;
import com.example.subway.Activity.TripHistory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
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

    public AccountFragment() {
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
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
                startActivity(new Intent(getActivity(), TripHistory.class));
            }
        });
        Button signOutButton = (Button) view.findViewById(R.id.signOut) ;
        TextView userNameTxt = (TextView) view.findViewById(R.id.userName);
        TextView emailTxt = (TextView) view.findViewById(R.id.email);
        TextView phoneNumberTxt = (TextView) view.findViewById(R.id.phoneNumber);
        TextView nationalIdTxt = (TextView) view.findViewById(R.id.nationalID);
        TextView editPassword = (TextView) view.findViewById(R.id.edit_password);

        databaseUser = FirebaseDatabase.getInstance().getReference("user");

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("configurations", Context.MODE_PRIVATE);
        String s = sharedPreferences.getString("user", null);
        if(s != null){
            User user = new User();
            user.fromJson(s);
             userNameTxt.setText(user.getFirstName()+ " "+ user.getLastName());
             emailTxt.setText(user.getEmail());
             phoneNumberTxt.setText(user.getPhoneNumberData());
             nationalIdTxt.setText(user.getNationalIdData());
        }

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("user",null);
                myEdit.apply();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });
        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePassword.class));
                getActivity().finish();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}