package com.example.bloodcareapplication;

public class User {

    private String firstName, lastName, phoneNum, icNum, username;

    private static final User instance = new User();

    public static User getInstance(){
        return instance;

    }
    public User(String firstName, String lastName, String phoneNum, String icNum, String carNumber,
                String carPlate, String carPlate2, String carPlate3, String carPlate4, String carPlate5) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.icNum = icNum;

    }

    public User() {
        super();
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getIcNum() {
        return icNum;
    }

    public void setIcNum(String icNum) {
        this.icNum = icNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




}
