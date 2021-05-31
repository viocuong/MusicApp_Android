package com.example.nguyenvancuong_project.model;

import java.io.Serializable;

public class Comment implements Serializable {
    private Person person;
    private Music music;
    private String content;

    public Comment(Person person, Music music, String content) {
        this.person = person;
        this.music = music;
        this.content = content;
    }

    public Person getPerson() {
        return person;
    }

    public Music getMusic() {
        return music;
    }

    public String getContent() {
        return content;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
