package com.example.api_news;

import com.google.gson.annotations.SerializedName;

public class Source {
    @SerializedName("name")
    private String sourceName;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
