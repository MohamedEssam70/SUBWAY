package com.example.subway;

public class User{
    private String firstName;
    private String lastName;
    private String nationalIdData;
    private String passwordData;
    private String phoneNumberData;
    private String email;

    public User() {
    }

    public User(String firstName, String lastName, String nationalIdData, String passwordData, String phoneNumberData, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalIdData = nationalIdData;
        this.passwordData = passwordData;
        this.phoneNumberData = phoneNumberData;
        this.email = email;
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
}
