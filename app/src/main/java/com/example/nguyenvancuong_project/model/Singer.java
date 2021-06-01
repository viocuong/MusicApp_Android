package com.example.nguyenvancuong_project.model;

import java.io.Serializable;

public class Singer implements Serializable {
    private String name;
    private String img;
    private String dob;

    public Singer(String name, String img, String dob) {
        this.name = name;
        this.img = img;
        this.dob = dob;
    }
    public Singer(String name) {
        this.name = name;
    }

    public Singer() {
    }
    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getDob() {
        return dob;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
