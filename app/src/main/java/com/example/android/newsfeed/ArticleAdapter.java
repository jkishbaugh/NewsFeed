package com.example.android.newsfeed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Date;
import java.util.List;

public class ArticleAdapter  extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>{

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        CardView article;
        TextView title, summary, author, byline, date;
        ImageView imageUrl;
        Date publishDate;


        public ArticleViewHolder(View itemView) {
            super(itemView);
            article = (CardView)itemView.findViewById(R.id.box);
            title= (TextView)itemView.findViewById(R.id.title);
            summary = itemView.findViewById(R.id.summary);
            author=itemView.findViewById(R.id.author);
            date = itemView.findViewById(R.id.date_published);
            imageUrl=itemView.findViewById(R.id.image);
        }

    }
    List<Article> articles;

    ArticleAdapter(List<Article> articles){
        this.articles= articles;
    }
    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_layout, viewGroup, false);
        ArticleViewHolder holder = new ArticleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {

        holder.title.setText(String.valueOf(articles.get(position).getHeadline()));
        holder.summary.setText(String.valueOf(articles.get(position).getSummary()));
        holder.author.setText(String.valueOf(articles.get(position).getByline()));


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
