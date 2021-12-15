package com.example.edocontrol;

import com.google.firebase.database.DatabaseReference;

public class User {

    private String name, email, password, phone;
    private DatabaseReference uid;

    public User() {
    }

    public User(String name, String email, String password, String phone, DatabaseReference uid) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid.toString();
    }

    public void setUid(DatabaseReference uid) {
        this.uid = uid;
    }
}
