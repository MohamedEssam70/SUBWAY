package com.example.subway;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class User{
    private String firstName;
    private String lastName;
    private String nationalIdData;
    private String passwordData;
    private String phoneNumberData;
    private String email;
    private double balance;

    public User() {
    }

    public User(String firstName, String lastName, String nationalIdData, String passwordData, String phoneNumberData, String email, double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalIdData = nationalIdData;
        this.passwordData = passwordData;
        this.phoneNumberData = phoneNumberData;
        this.email = email;
        this.balance = balance;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalIdData() {
        return nationalIdData;
    }

    public void setNationalIdData(String nationalIdData) {
        this.nationalIdData = nationalIdData;
    }

    public String getPasswordData() {
        return passwordData;
    }

    public void setPasswordData(String passwordData) {
        this.passwordData = passwordData;
    }

    public String getPhoneNumberData() {
        return phoneNumberData;
    }

    public void setPhoneNumberData(String phoneNumberData) {
        this.phoneNumberData = phoneNumberData;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public void fromJson(String json) {
        Type type = new TypeToken<User>(){}.getType();
        User user = new Gson().fromJson(json, type);
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.nationalIdData = user.nationalIdData;
        this.passwordData = user.passwordData;
        this.phoneNumberData = user.phoneNumberData;
        this.email = user.email;
        this.balance = user.balance;
    }
}
