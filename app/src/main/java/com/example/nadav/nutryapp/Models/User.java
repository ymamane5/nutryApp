package com.example.nadav.nutryapp.Models;

import java.util.ArrayList;

public class User {

    private String userName;
    private String userPassword;

    // user info
    private float userAge, userWeight, userHeight;

    private int userGender = -1;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userAge = -1;
        this.userWeight = -1;
        this.userHeight = -1;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserAge(float userAge) {
        this.userAge = userAge;
    }

    public float getUserAge() {
        return userAge;
    }

    public void setUserWeight(float userWeight) {
        this.userWeight = userWeight;
    }

    public float getUserWeight() {
        return userWeight;
    }

    public void setUserHeight(float userHeight) {
        this.userHeight = userHeight;
    }

    public float getUserHeight() {
        return userHeight;
    }

    public void setUserGender(int userGender) {
        this.userGender = userGender;
    }

    public int getUserGender() {
        return userGender;
    }

    // returns -1 if failed, BMI otherwhise
    public float getUserBMI() {
        if (userWeight == -1 || userAge == -1 || userHeight == -1)
            return -1;
        return (this.userWeight) / (this.userHeight * this.userHeight);
    }

    // returns -1 if failed, RMR otherwhise
    public float getUserBMR() {

        float bmr = -1;
        if (userWeight == -1 || userAge == -1 || userHeight == -1)
            return -1;
        if (this.userGender == GENDER_MALE)
            bmr = (float) (13.7 * this.userWeight + 5 * this.userHeight + 6.7 * this.userAge + 66);
        if (this.userGender == GENDER_FEMALE) {
            bmr = (float) (9.5 * this.userWeight + 1.8 * this.userHeight + 4.6 * this.userAge + 655);
        }
        return bmr;
    }





}
