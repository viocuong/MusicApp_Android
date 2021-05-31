package com.example.nguyenvancuong_project.model;

import java.io.Serializable;

public class ResultResponse implements Serializable {
    private String key;
    private String[] values;

    public ResultResponse(String key, String[] values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public String[] getValues() {
        return values;
    }
    public String toString(){
        return values[0];
    }
}
