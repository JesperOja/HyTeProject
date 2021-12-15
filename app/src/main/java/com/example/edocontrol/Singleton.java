package com.example.edocontrol;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class Singleton {
    private static final Singleton ourInstance = new Singleton();

    private List<User> users;
    private String name, email, password, phone;
    private DatabaseReference uid;


    public static Singleton getInstance(){
        return ourInstance;
    }
    private Singleton(){

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
    public void setUser(String password, String phone, String email, String name, DatabaseReference uid){
        users = new ArrayList<>();

        users.add(new User(name,email,password,phone,uid));
    }
    public String getUser(int i){
        String id =users.get(i).getUid();
        return id;
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
