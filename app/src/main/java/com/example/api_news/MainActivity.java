package com.example.api_news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    NewsAdapter adapter;
    RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    String myApiKey = "47a4d0842ada427986b39b3f86f80f5e";

    String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        refreshLayout = findViewById(R.id.swipe_refresh);
        refreshLayout.setColorSchemeResources(R.color.colorAccent);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews("");
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadNews("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Поиск");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                loadNews(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                loadNews(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void loadNews(String keyword){

        refreshLayout.setRefreshing(true);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NewsApiInterface api = retrofit.create(NewsApiInterface.class);

        final Call<NewsObj> request;
        if (keyword.length()>0){
            request = api.getSearchNews(keyword,"en","publishedAt", myApiKey);
        }else{
            request= api.getNews("us", myApiKey);
        }
// this is a comment
        request.enqueue(new Callback<NewsObj>() {
            @Override
            public void onResponse(Call<NewsObj> call, Response<NewsObj> response) {
                if (response.isSuccessful()){
                    NewsObj newsObj = response.body();
                    List<Article> articles = newsObj.getArticles();
                    adapter = new NewsAdapter(MainActivity.this, articles);
                    recyclerView.setAdapter(adapter);
                    refreshLayout.setRefreshing(false);

                    adapter.setRecyclerOnClick(new NewsAdapter.RecyclerOnClick() {
                        @Override
                        public void onClick(int position) {
                            Intent intent = new Intent(MainActivity.this, WebViewBrowser.class);
                            intent.putExtra("url", articles.get(position).getUrl());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<NewsObj> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Log.i("Error", t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}