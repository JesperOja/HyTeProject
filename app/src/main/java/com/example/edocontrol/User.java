package com.example.edocontrol;

import com.google.firebase.database.DatabaseReference;

/**
 * This class takes and returns user information
 * @author Anatolii Subbotin
 * @version 1.0 build 12.2021
 */
public class User {

    private String name, email, password, phone;
    private DatabaseReference uid;

    public User() {
    }

    /**
     * Constructor for the class
     * @param name user name
     * @param email user email
     * @param password use password
     * @param phone user phone number
     * @param uid user id on the FireBase server
     */
    public User(String name, String email, String password, String phone, DatabaseReference uid) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.uid = uid;
    }

    /**
     * Gives user name
     * @return returns user name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets user name
     * @param name name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    /**
     * Sets user email
     * @param email email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Gives user password
     * @return returns user password
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets user password
     * @param password password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Gives user phone number
     * @return returns user phone number
     */
    public String getPhone() {
        return phone;
    }
    /**
     * Sets user phone number
     * @param phone phone number of the user
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * Gives user id from FireBase server
     * @return returns user id from FireBase server
     */
    public String getUid() {
        return uid.toString();
    }
    /**
     * Sets user id from FireBase server
     * @param uid user id on the FireBase server
     */
    public void setUid(DatabaseReference uid) {
        this.uid = uid;
    }
}
