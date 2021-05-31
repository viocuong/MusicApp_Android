package com.example.nguyenvancuong_project.model;

import java.io.Serializable;

public class Favourite implements Serializable {
    private Person person;
    private Music music;
    private int num;

    public Favourite(Person person, Music music) {
        this.person = person;
        this.music = music;
    }

    public Person getPerson() {
        return person;
    }

    public Music getMusic() {
        return music;
    }

    public int getNum() {
        return num;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
