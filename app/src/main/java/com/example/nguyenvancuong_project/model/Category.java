package com.example.nguyenvancuong_project.model;

import java.io.Serializable;

public class Category implements Serializable {
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
