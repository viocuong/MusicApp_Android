package com.example.nguyenvancuong_project.model;

import java.io.Serializable;

public class Music implements Serializable {
    private Singer singer;
    private Category cagetory;
    private String name;
    private String url;
    private String image_url;
    private int view;
    public Music(Singer singer, Category cagetory, String name, String url, String img_url) {
        this.image_url = img_url;
        this.singer = singer;
        this.cagetory = cagetory;
        this.name = name;
        this.url = url;
    }
    public Music(Singer singer, Category cagetory, String name, String url, String img_url, int view) {
        this.view = view;
        this.image_url = img_url;
        this.singer = singer;
        this.cagetory = cagetory;
        this.name = name;
        this.url = url;
    }
    public int getView(){
        return this.view;
    }
    public Music(String name, String url) {
        this.name = name;
        this.url = url;
    }
    public String getImageUrl(){
        return this.image_url;
    }
    public Music() {
    }
    public Singer getSinger() {
        return singer;
    }

    public Category getCagetory() {
        return cagetory;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public void setCagetory(Category cagetory) {
        this.cagetory = cagetory;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
