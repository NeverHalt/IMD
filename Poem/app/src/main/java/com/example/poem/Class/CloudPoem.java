package com.example.poem.Class;

import cn.bmob.v3.BmobObject;

public class CloudPoem extends BmobObject {
    private String name = "无题";
    private String date = "";
    private String author = "匿名";
    private String content;
    private float favorite;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFavorite(float favorite) {
        this.favorite = favorite;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public float getFavorite() {
        return favorite;
    }
}
