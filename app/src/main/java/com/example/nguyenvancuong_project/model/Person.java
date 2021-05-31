package com.example.nguyenvancuong_project.model;

import android.net.Uri;

import java.io.Serializable;

public class Person implements Serializable {
    private String uid;
    private String name;
    private String photoUrl;
    private String email;
    private String password;

    public Person() {
    }

    public Person(String uid, String name, String photoUrl, String email, String password) {
        this.uid = uid;
        this.name = name;
        this.photoUrl = photoUrl;
        this.email = email;
        this.password = password;
    }

    public Person(String uid, String name, String photoUrl, String email) {
        this.uid = uid;
        this.name = name;
        this.photoUrl = photoUrl;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
