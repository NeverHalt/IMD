package com.example.poem.Class;

import android.util.Log;

public class Poem {
    private int id = -1;
    private String name = "无题";
    private String date = "";
    private String author = "匿名";
    private String content;
    private float favorite;
    private int position;
    private String summary = "";

    public Poem(int id, String name, String date, String author, String content, float favorite, int position) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.author = author;
        this.content = content;
        this.favorite = favorite;
        this.position = position;
        Log.v("110", "New an instance of Message");
    }

    public Poem(int id, String name, String date, String author, String content, float favorite) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.author = author;
        this.content = content;
        this.favorite = favorite;
        Log.v("110", "New an instance of Message");
    }

    public Poem(int id, String name, String date, String author, String content) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.author = author;
        this.content = content;
        Log.v("110", "New an instance of Message");
    }

    public Poem(String content) {
        this.content = content;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getContent() {
        return this.content;
    }

    public float getFavorite() {
        return this.favorite;
    }

    public int getPosition() {
        return this.position;
    }

    public String getSummary() {
        return this.summary;
    }
}
