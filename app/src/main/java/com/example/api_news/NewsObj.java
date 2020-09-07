package com.example.api_news;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsObj {

    private String status;
    private int totalResults;
    private List<Article> articles;

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
