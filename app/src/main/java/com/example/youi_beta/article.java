package com.example.youi_beta;

public class article {
    private String name;
    private String title;
    private String article;
    private String time;
    private float rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public article(String name, String title, String article, String time, float rating) {
        this.name = name;
        this.title = title;
        this.article = article;
        this.time = time;
        this.rating = rating;
    }
}