package com.example.subway;


import android.content.Context;
import android.widget.Toast;

public class Authentication {
    public static boolean checkNationalId(String nationalID){
        return nationalID.length() < 14;
    }

    public static boolean checkRequiredFields(Context context,String ... userData){
        for (String ss : userData){
            if (ss.isEmpty()){
                Toast.makeText(context, "Please Fill All Fields Required", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
