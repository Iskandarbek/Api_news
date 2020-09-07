package com.example.api_news;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiInterface {
    @GET("top-headlines")
    Call<NewsObj> getNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
    @GET("everything")
    Call<NewsObj>getSearchNews(
            @Query("q") String keyword,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey")String apiKey

    );


}
