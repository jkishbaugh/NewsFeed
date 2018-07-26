package com.example.android.newsfeed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedActivity extends AppCompatActivity {
    private static final String REQUEST_URL="https://content.guardianapis.com/search?order-by=newest&show-elements=image&show-fields=all&page-size=15&q=science%20and%20technology&api-key=43ea601e-62ba-4380-afa6-3b73b3691d5e";
    private  List<Article> articles = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feed_layout);

        initializeArticleList();





        RecyclerView articleRecyclerView = (RecyclerView) findViewById(R.id.container);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        articleRecyclerView.setLayoutManager(layoutManager);

        ArticleAdapter adapter = new ArticleAdapter(articles);
        articleRecyclerView.setAdapter(adapter);

        int loader = R.drawable.ic_launcher_foreground;
        ImageView image = (ImageView) findViewById(R.id.image);
        String imageUrl = articles.get(0).getImageResourceId();
        ImageLoader imageLoader = new ImageLoader(getApplicationContext());

        imageLoader.DisplayImage(imageUrl, loader, image);

    }

    private void initializeArticleList() {
        if(articles == null){
            articles = new ArrayList<>();
            articles.add(new Article("a","b","cdef",
                    "https://media.guim.co.uk/4e235a0c6aeaecb7640a18adb763f010c5acb556/201_644_5574_3344/500.jpg",
                    "author","01-01-2011"));

        }



    }
}
